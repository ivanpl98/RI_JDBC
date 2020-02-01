package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.persistence.CardGateway;
import uo.ri.persistence.PaymentMeanGateway;
import uo.ri.persistence.VoucherGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindPaymentMeanById {

    private Connection conn;
    private Long id;

    public FindPaymentMeanById(Connection conn, Long id) {
        this.conn = conn;
        this.id = id;
    }

    public PaymentMeanDto execute() throws SQLException {

        PaymentMeanGateway pmg = new PaymentMeanGateway(conn);
        VoucherGateway vg = new VoucherGateway(conn);
        CardGateway cg = new CardGateway(conn);
        PaymentMeanDto pm = pmg.findById(this.id);
        PaymentMeanDto tmp = null;

        if (pm instanceof CardDto) {
            tmp = cg.findById(pm.id);
            tmp.accumulated = pm.accumulated;
            tmp.clientId = pm.clientId;
        } else if (pm instanceof VoucherDto) {
            tmp = vg.findById(pm.id);
            tmp.accumulated = pm.accumulated;
            tmp.clientId = pm.clientId;
        } else {
            tmp = pm;
        }


        return tmp;
    }
}
