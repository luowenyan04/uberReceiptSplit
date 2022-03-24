package com.rtmart.uberreceiptsplit.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Data
public class UberEatsGuiItems {

    @JsonProperty(value = "STORE")
    private String store;

    @JsonProperty(value = "ORDER_UUID")
    private String orderUuid;

    @JsonProperty(value = "INS_DATE")
    private String insDate;

    @JsonProperty(value = "GUI_NO")
    private String guiNo;

    @JsonProperty(value = "TYPE")
    private Integer type;

    @JsonProperty(value = "ITEMS")
    private List<Item> items;

}
