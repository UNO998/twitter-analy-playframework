package actors;

import lyc.Item;
import lyc.SearchResult;
import lyc.TwitUserImpl;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * This class is to do the test for Message class.
 */
public class MessageTest {



    /**
     * Test the getter method and constructor of static class Keyword.
     */
    @Test
    public void TestKeywordGetter(){

        Message.Keyword keyword = new Message.Keyword("test");
        assertNotNull(keyword);
        assertEquals(keyword.getKeyword(),"test");

    }

    /**
     * Test the constructor of static class of User_id.
     */
    @Test
    public void TestUser_id(){


        Message.User_id user_id = new Message.User_id((long)1);
        assertNotNull(user_id);
        assertEquals(user_id.getUser_id(),1);

    }

    /**
     * Test the constructor of static class Tweet.
     */
    @Test
    public void TestTweet(){
        TwitUserImpl userbase = new TwitUserImpl((long)1,new String("user"),new String("screenName"));
        Date date = new Date();
        Item item = new Item(userbase,new String("text"),date);
        Message.Tweet tweet = new Message.Tweet(item);
        assertNotNull(tweet);

    }

    /**
     * Test the constructor and getter of static class Update.
     */
    @Test
    public void TestUpdate(){
        CompletableFuture<List<SearchResult>> searchResultList = new CompletableFuture<>();
        Message.Update update = new Message.Update(searchResultList);
        assertNotNull(update);
        assertEquals(update.getTweets(),searchResultList);
    }



}
