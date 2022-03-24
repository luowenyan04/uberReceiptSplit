package com.rtmart.uberreceiptsplit.Controller;

import com.rtmart.uberreceiptsplit.Entity.Item;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import com.rtmart.uberreceiptsplit.Service.UberEatsGuiItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/uberReceiptSplit", produces = MediaType.APPLICATION_JSON_VALUE)
public class UberEatsGuiItemsController {

    private UberEatsGuiItemsService itemsService;

    @Autowired
    public UberEatsGuiItemsController(UberEatsGuiItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping("/MemoSplit")
    public String SplitMemo(@RequestBody MultiValueMap<String ,UberEatsGuiItems> request) {

        UberEatsGuiItems uberEatsGuiItems = new UberEatsGuiItems();
        uberEatsGuiItems.setStore(String.valueOf(request.get("STORE").get(0)));
        uberEatsGuiItems.setOrderUuid(String.valueOf(request.get("ORDER_UUID").get(0)));
        uberEatsGuiItems.setInsDate(String.valueOf(request.get("INS_DATE").get(0)));
        uberEatsGuiItems.setGuiNo(String.valueOf(request.get("GUI_NO").get(0)));
        uberEatsGuiItems.setType(Integer.valueOf(String.valueOf(request.get("TYPE").get(0))));

        itemsService.delItemExists(uberEatsGuiItems);
        List<Item> memo = itemsService.getItemsMemo(uberEatsGuiItems);
        (uberEatsGuiItems).setItems(memo);
        itemsService.insUberEatsGuiItems(uberEatsGuiItems.getStore(), uberEatsGuiItems);
        return "0";
    }

}
