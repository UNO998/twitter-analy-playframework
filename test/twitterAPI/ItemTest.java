package lyc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Date;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * test Item class
 */
public class ItemTest {

    private Item item;
    private Item item2;

    /**
     * config the class, set install value
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        UserBase user = new TwitUserImpl(111, "test", "test");

        item = new Item(user, "test", new Date());
        item2 = new Item(user, "test", new Date());

    }


    /**
     * test the get and set method
     */
    @Test
    public void TestGetterAndSetter(){
        Date now = new Date();

        item.setText("this is a test.");
        item.setCreated_time(now);

        assertEquals("this is a test.", item.getText());
        assertEquals(now, item.getCreated_time());
        assertEquals("www.twitter.com/test", item.getUser_link());
        assertFalse(item.equals("1"));
        assertFalse(item.equals(item2));


    }


}