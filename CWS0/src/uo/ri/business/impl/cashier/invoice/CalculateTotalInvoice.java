package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.*;
import uo.ri.persistence.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CalculateTotalInvoice {

    private Connection conn;
    private List<Long> wos;

    public CalculateTotalInvoice(Connection conn, List<Long> wos) {
        this.conn = conn;
        this.wos = wos;
    }

    public double execute() throws SQLException {
        double totalInvoice = 0.0;
        for (Long id : wos) {
            double laborTotal = checkTotalLabor(conn, id);
            double sparesTotal = checkTotalParts(conn, id);
            double workTotal = laborTotal + sparesTotal;

            updateWorkorderTotal(conn, id, workTotal);

            totalInvoice += workTotal;
        }
        return totalInvoice;
    }

    private double checkTotalLabor(Connection conn, Long id) throws SQLException {
        IntervetionGateway ig = new IntervetionGateway(conn);
        VehicleGateway vg = new VehicleGateway(conn);
        VehicleTypeGateway vtg = new VehicleTypeGateway(conn);
        WorkorderGateway wg = new WorkorderGateway(conn);

        WorkOrderDto wo = wg.findById(id);
        List<InterventionDto> is = ig.findByWorkorder(id);
        VehicleDto v = vg.findById(wo.vehicleId);
        VehicleTypeDto vt = vtg.findById(v.vehicleTypeId);

        return is.stream().mapToDouble(x -> x.minutes).reduce(0,
                (a, b) -> a + (b * (vt.pricePerHour / 60)));
    }

    private double checkTotalParts(Connection conn, Long id) throws SQLException {
        IntervetionGateway ig = new IntervetionGateway(conn);
        SubstitutionGateway sg = new SubstitutionGateway(conn);
        SparePartGateway spg = new SparePartGateway(conn);

        List<InterventionDto> is = ig.findByWorkorder(id);
        double total = 0;
        for (InterventionDto i : is) {
            List<SubstitutionDto> ss = sg.findByIntervention(i.id);
            for (SubstitutionDto s : ss) {
                SparePartDto sp = spg.findById(s.sparepart_id);
                total += s.quantity * sp.price;
            }
        }
        return total;
    }

    private void updateWorkorderTotal(Connection conn, Long id,
                                      double workTotal) throws SQLException {
        WorkorderGateway wg = new WorkorderGateway(conn);
        WorkOrderDto wo = wg.findById(id);
        wo.total = workTotal;
        wg.update(wo);
    }


}
