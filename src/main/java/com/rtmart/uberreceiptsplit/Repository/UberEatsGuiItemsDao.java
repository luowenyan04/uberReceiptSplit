package com.rtmart.uberreceiptsplit.Repository;

import com.rtmart.uberreceiptsplit.RowMapper.OrderMemoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UberEatsGuiItemsDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UberEatsGuiItemsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public String getOrdersMemo(String guiNo, Integer type, String insDate, String orderUuid) {
        String sql = "select memo " +
                "from uber_eats_gui " +
                "where gui_no = :guiNo " +
                "and type = :type " +
                "and ins_date = to_date(:insDate, 'yyyymmddhh24miss') " +
                "and order_uuid = :orderUuid";

        Map<String ,Object> map = new HashMap<>();
        map.put("guiNo", guiNo);
        map.put("type", type);
        map.put("insDate", insDate);
        map.put("orderUuid", orderUuid);

        List<String> list = namedParameterJdbcTemplate.query(sql, map, new OrderMemoRowMapper());

        return list.get(0);
    }
}
