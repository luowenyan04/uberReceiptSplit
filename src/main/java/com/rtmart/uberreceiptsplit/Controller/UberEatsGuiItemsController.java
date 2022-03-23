package com.rtmart.uberreceiptsplit.Controller;

import com.rtmart.uberreceiptsplit.Entity.Item;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import com.rtmart.uberreceiptsplit.Service.UberEatsGuiItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/uberReceiptSplit", produces = MediaType.APPLICATION_JSON_VALUE)
public class UberEatsGuiItemsController {

    private UberEatsGuiItemsService itemsService;

    @Autowired
    public UberEatsGuiItemsController(UberEatsGuiItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping("/MemoSplit")
    public UberEatsGuiItems SplitMemo(@RequestBody UberEatsGuiItems request) {
        itemsService.chkItemExists(request);
        List<Item> memo = itemsService.getItemsMemo(request);
        request.setItems(memo);
        itemsService.insUberEatsGuiItems(request.getStore(), request);
        return request;
    }

}
