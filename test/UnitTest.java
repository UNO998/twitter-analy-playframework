import models.Actor;
import models.Twitter;
import models.User;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit testing does not require Play application start up.
 *
 * https://www.playframework.com/documentation/latest/JavaTest
 */
public class UnitTest {

    private User user;
    private Actor actor;
    private Twitter twitter;

    @Before
    public void setup() {
        user = new User("12345", "asdf", "zxcv", "qwer", "qwer1234", 123, 234, 345, "kkk");
        actor = new Actor("name", user);
        twitter = new Twitter("123");
    }

    @Test
    public void testUser() {
        assertEquals(user.getId(),"12345");
        assertEquals(user.getName(),"asdf");
        assertEquals(user.getScreen_name(),"zxcv");
        assertEquals(user.getDescription(),"qwer");
        assertEquals(user.getProfile_image_url(),"qwer1234");
        assertEquals(user.getFollowers(),123);
        assertEquals(user.getPosts(),234);
        assertEquals(user.getFriends(),345);
    }

    @Test
    public void testActor() {
        assertEquals(actor.getText(),"name");
        assertEquals(actor.getUser(),user);
    }

    @Test
    public void testTwitter() {
        assertEquals(twitter.hashtag,"123");
    }
}
