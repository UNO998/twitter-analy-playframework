package lyc;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.Objects;

/**
 * search tweets result
 */
public class SearchResult {
	private String keyword;
	private List<Item> tweets;

	/**
	 * constructor
	 * @param keyword key word use to search
	 * @param tweets the tweets
	 */
	public SearchResult(String keyword, List<Item> tweets){
		this.keyword = keyword;
		this.tweets = tweets;
	}

	/**
	 * get key word
	 * @return key word
	 */
	public String getKeyword(){
		return keyword;
	}

	/**
	 * get tweets
	 * @return tweets
	 */
	public List<Item> getTweets(){
		return tweets;
	}

	/**
	 * override equals method
	 * @param other
	 * @return
	 */
	@Override
    public boolean equals(Object other){
        if(other == this)
            return true;
        if( !(other instanceof SearchResult) )
            return false;

        SearchResult right = (SearchResult) other;
        return keyword.equals(right.keyword) && tweets.equals(right.tweets);
    }

	/**
	 * override the hashCode method
	 * @return
	 */
	@Override
    public int hashCode() {
        return Objects.hash(keyword, tweets);
    }
}