package com.rtmart.uberreceiptsplit.Entity;

import lombok.Data;

@Data
public class Item {
    private String itemName;
    private Integer itemNo;
    private Integer salesAmount;
    private Integer qty;
    private Integer totalAmount;
    private Boolean isDiscount;
}
