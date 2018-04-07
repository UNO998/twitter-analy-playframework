package lyc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Date;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class ItemTest {

    private Item item;

    @Before
    public void setUp() throws Exception {
        UserBase user = new TwitUserImpl(111, "test", "test");
        item = new Item(user, "test", new Date());
    }


    @Test
    public void TestGetterAndSetter(){
        Date now = new Date();

        item.setText("this is a test.");
        item.setRef_user("ref");
        item.setCreated_time(now);

        assertEquals("this is a test.", item.getText());
        assertEquals("ref", item.getRef_user());
        assertEquals(now, item.getCreated_time());
        assertEquals("www.twitter.com/test", item.getUser_link());

    }


}