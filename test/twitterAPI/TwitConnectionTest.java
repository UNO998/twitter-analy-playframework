package lyc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * test twitConnection method
 */
public class TwitConnectionTest {

    private TwitConnection account;

    /**
     * config the install auths, create the twitter account factory
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        String []auths = new String[]{
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };


        TwitterAccountFactory t_factory = new TwitterAccountFactory();
        account = t_factory.createAccount(auths);
    }

    /**
     * test the constructor
     */
    @Test
    public void TestConstructor(){
        assertNotNull(account);
    }

    /**
     * test getcurrentuser method
     */
    @Test
    public void TestGetCurrentUser(){
        UserBase user = account.getCurrentUser();
        assertEquals(user.getUser_screenName(), "WingCueng_Ray");

        user = account.getCurrentUser();
        assertEquals(user.getUser_screenName(), "WingCueng_Ray");
    }

    /**
     * test serchPost method
     */
    @Test
    public void TestSearchPost(){
        List<Item> results = account.SearchPost("hello", 10);
        assertEquals(results.size(), 10);

        for(Item item : results){
            assertThat(item.getUser_name(), notNullValue());
            assertThat(item.getText(), notNullValue());
            assertTrue(item.getUser_id() > 0);
        }

        results = account.SearchPost("hello");
        assertTrue(results.size() > 10);
    }

    /**
     * test GetSelfHomeLine method
     */
    @Test
    public void TestGetSelfHomeLine(){
        UserBase currUser = account.getCurrentUser();
        List<Item> results = account.getSelfHomeLine();

        assertNotNull(results);
        assertNotEquals(results.size(), 0);
    }

    /**
     * test GetHomeLineById method
     */
    @Test
    public void TestGetHomeLineById(){
        long id = 2965074672L;
        List<Item> results = account.getHomeLineById(id);

        assertNotNull(results);
        assertNotEquals(results.size(), 0);
        for(Item item : results){
            assertEquals(item.getUser_id(), id);
        }
    }

    /**
     * end the test case
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }
}