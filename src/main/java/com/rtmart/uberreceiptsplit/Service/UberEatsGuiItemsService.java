package com.rtmart.uberreceiptsplit.Service;

import com.rtmart.uberreceiptsplit.Entity.Item;
import com.rtmart.uberreceiptsplit.Entity.UberEatsGuiItems;
import com.rtmart.uberreceiptsplit.Repository.UberEatsGuiItemsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UberEatsGuiItemsService {

    private static final Pattern pattern = Pattern.compile("(\\d+)-(-?\\d+) x (\\d+) = (-?\\d+)");

    private UberEatsGuiItemsDao itemsDao;

    @Autowired
    public UberEatsGuiItemsService(UberEatsGuiItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    public List<Item> getItemsMemo(UberEatsGuiItems request) {
        String guiNo = request.getGuiNo();
        Integer type = request.getType();
        String insDate = request.getInsDate();
        String orderUuid = request.getOrderUuid();

        String memo = itemsDao.getOrdersMemo(guiNo, type, insDate, orderUuid);

        return SplitMemoProc(memo);
    }

    public List<Item> SplitMemoProc(String memo) {
        String[] list = memo.split("\\|\\|");

        List<Item> itemsList = new ArrayList<>();

        for (String s : list) {
            String[] tmp = s.split("#");

            Item item = new Item();
            item.setItemName(tmp[0].substring(tmp[0].indexOf(".") + 1).trim());
            item.setIsDiscount(tmp[0].substring(tmp[0].indexOf(".") + 1).contains("Discount on"));

            Matcher itemMatcher = checkString(tmp[1]);
            item.setItemNo(Integer.parseInt(itemMatcher.group(1)));
            item.setSalesAmount(Integer.parseInt(itemMatcher.group(2)));
            item.setQty(Integer.parseInt(itemMatcher.group(3)));
            item.setTotalAmount(Integer.parseInt(itemMatcher.group(4)));

            System.out.println(item);

            Optional<Item> itemOptional = itemsList.stream()
                    .filter(i -> i.getItemNo().equals(item.getItemNo()))
                    .findFirst();

            if (itemOptional.isPresent()) {
                Item itemTmp = itemOptional.get();
                itemTmp.setSalesAmount(itemTmp.getSalesAmount() + item.getSalesAmount());
                itemTmp.setTotalAmount(itemTmp.getTotalAmount() + item.getTotalAmount());

                // 折扣的數量不會列入計算
                if (!item.getIsDiscount()) {
                    itemTmp.setQty(itemTmp.getQty() + item.getQty());
                }

                itemsList.set(itemsList.indexOf(itemOptional.get()), itemTmp);
            } else {
                itemsList.add(item);
            }
        }

        return itemsList;
    }

    public static Matcher checkString(String s) {
        Matcher matcher = pattern.matcher(s);
        if (!matcher.matches())
            System.out.println(s);
        return matcher;
    }

}
