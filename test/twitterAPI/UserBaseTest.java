package lyc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class UserBaseTest {

    private UserBase user;

    @Before
    public void setUp() throws Exception {
        user = new TwitUserImpl(111, "test", "test");
    }


    @Test
    public void TestGetterAndSetter(){
        user.setUser_id(12345);
        user.setUser_link("www.google.com");
        user.setUser_name("Hello World");
        user.setUser_screenName("hello");

        assertEquals(12345, user.getUser_id());
        assertEquals("www.google.com", user.getUser_link());
        assertEquals("Hello World", user.getUser_name());
        assertEquals("hello", user.getUser_screenName());
    }


}