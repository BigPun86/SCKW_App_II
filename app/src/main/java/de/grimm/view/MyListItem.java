package de.grimm.view;

/**
 * Created by Adel on 05.07.2015.
 */
public class MyListItem {

    private String itemTitle, itemSubTitle;

    public String getItemSubTitle() {
        return itemSubTitle;
    }

    public void setItemSubTitle(String itemSubTitle) {
        this.itemSubTitle = itemSubTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public MyListItem(String title, String subTitle) {
        this.itemTitle = title;
        this.itemSubTitle = subTitle;
    }
}