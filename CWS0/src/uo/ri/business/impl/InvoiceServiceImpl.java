package uo.ri.business.impl;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.*;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.cashier.invoice.*;
import uo.ri.common.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InvoiceServiceImpl implements InvoiceService {
    @Override
    public InvoiceDto createInvoiceFor(List<Long> idsAveria) throws BusinessException {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            CheckWorkOrders cwo = new CheckWorkOrders(conn, idsAveria);
            GenerateInvoiceNumber gin = new GenerateInvoiceNumber(conn);
            CalculateTotalInvoice cti =
                    new CalculateTotalInvoice(conn, idsAveria);

            cwo.execute();

            InvoiceDto i = new InvoiceDto();

            i.number = gin.execute();
            i.date = Dates.today();
            double amount = cti.execute(); // not vat included
            i.vat = vatPercentage(i.date);
            i.total = Round.twoCents(amount * (1 + i.vat / 100));

            CreateInvoice ci = new CreateInvoice(conn, i);
            ci.execute();
            FindByNumber fn = new FindByNumber(conn, i.number);
            i = fn.execute();
            LinkWorkorderInvoice lwi = new LinkWorkorderInvoice(conn, idsAveria, i.id);
            lwi.execute();
            MarkWorkorderInvoiced mwi = new MarkWorkorderInvoiced(conn, idsAveria);
            mwi.execute();
            conn.commit();
            return i;
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

    @Override
    public InvoiceDto findInvoice(Long numeroFactura) {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            FindByNumber fn = new FindByNumber(conn, numeroFactura);
            return fn.execute();
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

    @Override
    public List<PaymentMeanDto> findPayMethodsForInvoice(Long idFactura) {
        Connection conn = null;
        List<PaymentMeanDto> pms = new ArrayList<>();
        FindById fn;
        FindWorkOrders fwo;
        FindClient fc;
        FindPaymentMeans fpm;
        List<WorkOrderDto> wos;
        List<Long> client_ids = new ArrayList<>();
        try {
            //Establish connection
            conn = Jdbc.getConnection();

            InvoiceDto i;
            //Find invoice by id
            fn = new FindById(conn, idFactura);
            i = fn.execute();
            //Find workorders associated to invoice
            fwo = new FindWorkOrders(conn, i.id);
            wos = fwo.execute();
            //For each work order, find the client of the vehicle
            for (WorkOrderDto wo : wos) {
                fc = new FindClient(conn, wo);
                Long id = fc.execute();
                if (!client_ids.contains(id)) {
                    client_ids.add(fc.execute());
                }
            }
            //For each client find its payment means
            for (Long c : client_ids) {
                fpm = new FindPaymentMeans(conn, c);
                pms.addAll(fpm.execute());
            }
            //Return all payment means
            return pms;
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

    @Override
    public void settleInvoice(Long idFactura, Map<Long, Double> cargos) throws BusinessException {
        Connection conn = null;
        FindById fn;
        FindPaymentMeanById fpm;
        UpdatePaymentMean upm;
        MarkInvoiceAsPaid mi;
        InvoiceDto i;
        double total = 0;
        double charge;
        PaymentMeanDto pm;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            fn = new FindById(conn, idFactura);
            i = fn.execute();
            for (Long l : cargos.keySet())
                total += cargos.get(l);
            if (total != i.total)
                throw new BusinessException("Total amount introduced and " +
                        "invoice total are not equal");
            for (Long l : cargos.keySet()) {
                fpm = new FindPaymentMeanById(conn, l);
                pm = fpm.execute();
                charge = cargos.get(l);
                if (pm instanceof VoucherDto)
                    if (charge > ((VoucherDto) pm).available)
                        throw new BusinessException("The charge exceeds the " +
                                "voucher available amount.");
                upm = new UpdatePaymentMean(conn, pm, charge);
                upm.execute();
            }
            mi = new MarkInvoiceAsPaid(conn, i.id);
            mi.execute();
            conn.commit();
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }

    }

    @Override
    public List<WorkOrderDto> findRepairsByClient(String dni) throws BusinessException {
        Connection conn = null;
        List<WorkOrderDto> wos = new ArrayList<>();
        try {
            conn = Jdbc.getConnection();
            FindByDni fd = new FindByDni(conn, dni);
            ClientDto c = fd.execute();
            FindVehiclesByClient fv = new FindVehiclesByClient(conn, c.id);
            List<VehicleDto> vs = fv.execute();
            for (VehicleDto v : vs) {
                FindUnchargedWorkorders fw = new FindUnchargedWorkorders(conn, v.id);
                wos.addAll(fw.execute());
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
        return wos;
    }

    private double vatPercentage(Date dateInvoice) {
        return Dates.fromString("1/7/2012").before(dateInvoice) ? 21.0 : 18.0;
    }
}
