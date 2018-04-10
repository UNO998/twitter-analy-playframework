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
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Test SearchResult get method
 */
public class SearchResultTest {
    private SearchResult searchResult;
    private SearchResult test;
    private List<Item> list;
    private List<Item> list2;

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
        list2 = new ArrayList<>();
        list.add(item);
        list2.add(item);
        list2.add(new Item(userbase, new String("text2"),date));

        searchResult = new SearchResult("today", list);

         test = new SearchResult("today", list2);
    }

    /**
     * Test get key word method
     */
    @Test
    public void TestgetKeyword(){
        assertEquals("today" ,  searchResult.getKeyword());
        assertTrue(searchResult.equals(searchResult));
        assertFalse(searchResult.equals("1"));
        assertFalse(searchResult.equals(test));


    }

    /**
     * Test get tweets method
     */
    @Test
    public void TestgetTweets(){
        assertEquals( list , searchResult.getTweets());
    }

}
