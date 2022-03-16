package com.rtmart.uberreceiptsplit.Service;

import com.rtmart.uberreceiptsplit.Entity.Items;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import com.rtmart.uberreceiptsplit.Repository.UberEatsGuiItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UberEatsGuiItemsService {

    private UberEatsGuiItemsDao itemsDao;

    @Autowired
    public UberEatsGuiItemsService(UberEatsGuiItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    public String getItemsMemo(UberEatsGuiItems request) {
        String guiNo = request.getGuiNo();
        Integer type = request.getType();
        String insDate = request.getInsDate();
        String orderUuid = request.getOrderUuid();

        String memo = itemsDao.getOrdersMemo(guiNo, type, insDate, orderUuid);

        return memo;
    }

}
