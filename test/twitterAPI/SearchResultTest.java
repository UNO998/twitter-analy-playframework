package twitterAPI;

import lyc.Item;
import lyc.SearchResult;
import lyc.TwitUserImpl;
import lyc.UserBase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Test SearchResult get method
 */
public class SearchResultTest {
    private SearchResult searchResult;
    private List<Item> list;

    /**
     * config the class
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        TwitUserImpl userbase = new TwitUserImpl((long)1, new String("user"), new String("screenName"));
        Date date = new Date();
        Item item = new Item(userbase, new String("text"),date);
        list = new ArrayList<>();
        list.add(item);
        searchResult = new SearchResult("today", list);
    }

    /**
     * Test get key word method
     */
    @Test
    public void TestgetKeyword(){
        assertEquals("today" ,  searchResult.getKeyword());
    }

    /**
     * Test get tweets method
     */
    @Test
    public void TestgetTweets(){
        assertEquals( list , searchResult.getTweets());
    }

}
