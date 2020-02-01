package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.persistence.PaymentMeanGateway;
import uo.ri.persistence.VoucherGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdatePaymentMean {

    private Connection conn;
    private PaymentMeanDto pm;
    private Double charge;

    public UpdatePaymentMean(Connection conn, PaymentMeanDto pm,
                             Double charge) {
        this.conn = conn;
        this.pm = pm;
        this.charge = charge;
    }

    public void execute() throws SQLException {
        PaymentMeanGateway pmg = new PaymentMeanGateway(conn);
        VoucherGateway vg = new VoucherGateway(conn);

        pm.accumulated += this.charge;
        pmg.update(pm);
        if (pm instanceof VoucherDto) {
            ((VoucherDto) pm).available -= this.charge;
            vg.update((VoucherDto) pm);
        }
    }
}
