package com.xxmassdeveloper.mpchartexample.notimportant;

/**
 * Created by Philipp Jahoda on 07/12/15.
 */
public class ContentItem {

    String name;
    String desc;
    boolean isNew = false;

    public ContentItem(String n, String d) {
        name = n;
        desc = d;
    }
    public static ContentItem createNew(String n, String d){
        ContentItem contentItem = new ContentItem(n,d);
        contentItem.isNew = true;
        return contentItem;
    }
}
