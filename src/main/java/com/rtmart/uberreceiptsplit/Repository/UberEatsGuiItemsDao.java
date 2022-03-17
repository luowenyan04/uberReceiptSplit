package com.rtmart.uberreceiptsplit.Repository;

import com.rtmart.uberreceiptsplit.Entity.Item;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import com.rtmart.uberreceiptsplit.RowMapper.OrderMemoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

        Map<String, Object> map = new HashMap<>();
        map.put("guiNo", guiNo);
        map.put("type", type);
        map.put("insDate", insDate);
        map.put("orderUuid", orderUuid);

        List<String> list = namedParameterJdbcTemplate.query(sql, map, new OrderMemoRowMapper());

        return list.get(0);
    }

    public String insUberEatsGuiItems(UberEatsGuiItems uberEatsGuiItems) {
        String sql = "insert into uber_eats_gui_items (" +
                "order_uuid, " +
                "ins_date, " +
                "gui_no, " +
                "type, " +
                "item_no, " +
                "sales_amount, " +
                "qty, " +
                "total_price " +
                ") values ( " +
                ":orderUuid, " +
                "to_date(:insDate, 'yyyymmddhh24miss'), " +
                ":guiNo, " +
                ":type, " +
                ":itemNo, " +
                ":salesAmount, " +
                ":qty, " +
                ":totalAmount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[uberEatsGuiItems.getItems().size()];

        for (int i = 0; i < uberEatsGuiItems.getItems().size(); i++) {
            Item item = uberEatsGuiItems.getItems().get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderUuid", uberEatsGuiItems.getOrderUuid());
            parameterSources[i].addValue("insDate", uberEatsGuiItems.getInsDate());
            parameterSources[i].addValue("guiNo", uberEatsGuiItems.getGuiNo());
            parameterSources[i].addValue("type", uberEatsGuiItems.getType());
            parameterSources[i].addValue("itemNo", item.getItemNo());
            parameterSources[i].addValue("salesAmount", item.getSalesAmount());
            parameterSources[i].addValue("qty", item.getQty());
            parameterSources[i].addValue("totalAmount", item.getTotalAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
        return "Insert into UberEatsGuiItems 完成";
    }
}
