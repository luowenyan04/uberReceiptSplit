package com.rtmart.uberreceiptsplit.Repository;

import com.rtmart.uberreceiptsplit.Entity.Item;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UberEatsGuiItemsDao {

    @Autowired
    @Qualifier("stJDBC")
    private Map<String, NamedParameterJdbcTemplate> namedParameterJdbcTemplateMap;

    public String getOrdersMemo(String storeNo, String guiNo, Integer type, String insDate, String orderUuid) {
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

        return namedParameterJdbcTemplateMap.get(storeNo).queryForObject(sql, map, String.class);
    }

    public String insUberEatsGuiItems(String storeNo, UberEatsGuiItems uberEatsGuiItems) {
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

        namedParameterJdbcTemplateMap.get(storeNo).batchUpdate(sql, parameterSources);
        return "Insert into UberEatsGuiItems 完成";
    }

    public void delUberEatsItems(String storeNo, String guiNo, Integer type, String insDate, String orderUuid) {
        String sql = "delete uber_eats_gui_items " +
                "where gui_no = :guiNo " +
                "and type = :type " +
                "and ins_date = to_date(:insDate, 'yyyymmddhh24miss') " +
                "and order_uuid = :orderUuid";

        Map<String, Object> map = new HashMap<>();
        map.put("guiNo", guiNo);
        map.put("type", type);
        map.put("insDate", insDate);
        map.put("orderUuid", orderUuid);

        namedParameterJdbcTemplateMap.get(storeNo).update(sql, map);
    }
}
