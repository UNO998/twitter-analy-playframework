package controllers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * test widgetDate class
 */
public class WidgetControllerTest {

    private WidgetData widgetData;

    /**
     * test the get and set method
     */
    @Test
    public void TestIndex(){
        widgetData = new WidgetData();
        widgetData.setKeyword("test");
        assertNotNull(widgetData.getKeyword());
        assertEquals("test", widgetData.getKeyword());
    }
}