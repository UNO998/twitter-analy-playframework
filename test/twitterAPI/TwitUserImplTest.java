package twitterAPI;

import lyc.TwitUserImpl;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Test twitUserImpl method
 */
public class TwitUserImplTest {
    private TwitUserImpl twitUser;

    /**
     * config the class
     */
    @Before
    public void setUp(){
        twitUser = new TwitUserImpl((long)1, "tester", "tester");
    }


    /**
     * test the equals method
     */
    @Test
    public void TestEqual(){
        TwitUserImpl test = new TwitUserImpl((long)1, "tester", "tester");
        assertFalse(test.equals("1"));
        assertEquals(true, twitUser.equals(test));
    }
}
