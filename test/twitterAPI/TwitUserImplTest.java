package twitterAPI;

import lyc.TwitUserImpl;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

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
        assertEquals(true, twitUser.equals(test));
    }
}
