package uo.ri.business.impl.cashier.invoice;

import alb.util.date.Dates;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.persistence.CardGateway;
import uo.ri.persistence.PaymentMeanGateway;
import uo.ri.persistence.VoucherGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindPaymentMeans {

    private Connection conn;
    private Long c;

    public FindPaymentMeans(Connection conn, Long c) {
        this.conn = conn;
        this.c = c;
    }

    public List<PaymentMeanDto> execute() throws SQLException {
        List<PaymentMeanDto> tmps = new ArrayList<>();
        PaymentMeanGateway pmg = new PaymentMeanGateway(conn);
        CardGateway cg = new CardGateway(conn);
        VoucherGateway vg = new VoucherGateway(conn);
        List<PaymentMeanDto> pms = new ArrayList<>(pmg.findByUser(c));
        for (PaymentMeanDto pm : pms) {
            if (pm instanceof CardDto) {
                CardDto tmp = cg.findById(pm.id);
                if (!tmp.cardExpiration.before(Dates.today())) {
                    tmp.id = pm.id;
                    tmp.accumulated = pm.accumulated;
                    tmp.clientId = pm.clientId;
                    tmps.add(tmp);
                }
            } else if (pm instanceof VoucherDto) {
                VoucherDto tmp = vg.findById(pm.id);
                tmp.id = pm.id;
                tmp.accumulated = pm.accumulated;
                tmp.clientId = pm.clientId;
                tmps.add(tmp);
            } else {
                tmps.add(pm);
            }
        }
        return tmps;
    }
}
