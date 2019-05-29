package com.cy.view.popupwindow;

import java.util.ArrayList;
import java.util.List;

public class PopupItem {
    public String itemName;
    public String itemIcon;

    public PopupItem(String itemName) {
        this.itemName = itemName;
    }

    public PopupItem(String itemName, String itemIcon) {
        this.itemName = itemName;
        this.itemIcon = itemIcon;
    }

    public static List<PopupItem> convert(List<String> datas){
        List<PopupItem> popupItems=new ArrayList<>();
        for (String data:datas){
            popupItems.add(new PopupItem(data));
        }
        return popupItems;
    }
}
