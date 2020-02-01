package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CardDto;
import uo.ri.common.Conf;
import uo.ri.common.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardGateway extends AbstractGateway implements Gateway<CardDto> {

    public CardGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(CardDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_CARD"));

        pst.setLong(1, obj.id);
        pst.setString(2, obj.cardNumber);
        pst.setString(3, obj.cardType);
        pst.setDate(4, Util.convertToSqlDate(obj.cardExpiration));

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_CARD"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(CardDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_CARD"));

        pst.setString(1, obj.cardNumber);
        pst.setString(2, obj.cardType);
        pst.setDate(3, Util.convertToSqlDate(obj.cardExpiration));
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<CardDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_CARDS"));
        List<CardDto> cs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return cs;
    }

    public CardDto findById(Long id) throws SQLException {
        CardDto c = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_CARD_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next())
            c = resultSetToCard(rs);

        Jdbc.close(rs, pst);

        return c;
    }

    private List<CardDto> resultSetToList(ResultSet rs) throws SQLException {
        List<CardDto> cs = new ArrayList<>();
        while (rs.next())
            cs.add(resultSetToCard(rs));
        rs.close();
        return cs;
    }

    private CardDto resultSetToCard(ResultSet rs) throws SQLException {
        CardDto c = new CardDto();
        c.id = rs.getLong("id");
        c.cardNumber = rs.getString("number");
        c.cardType = rs.getString("type");
        c.cardExpiration = Util.convertFromSqlDate(rs.getDate("validthru"));
        return c;
    }
}
