package de.grimm.dataModel;

/**
 * Created by Adel on 07.07.2015.
 */
public class DataObjectContainer {

    private String[] titles, text;

    public DataObjectContainer(String[] titles, String[] text) {
        this.titles = titles;
        this.text = text;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }


}
