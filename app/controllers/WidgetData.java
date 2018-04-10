package controllers;

import play.data.validation.Constraints;

/**
 * A form processing DTO that maps to the widget form.
 *
 * Using a class specifically for form binding reduces the chances
 * of a parameter tampering attack and makes code clearer, because
 * you can define constraints against the class.
 */
public class WidgetData {

    @Constraints.Required
    private String keyword;

    /**
     * constructor
     */
    public WidgetData() {
    }

    /**
     * get key word
     * @return key word
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * set key word
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }



}
