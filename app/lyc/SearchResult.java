package lyc;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.Objects;


public class SearchResult {
	private String keyword;
	private List<Item> tweets;

	public SearchResult(String keyword, List<Item> tweets){
		this.keyword = keyword;
		this.tweets = tweets;
	}

	public String getKeyword(){
		return keyword;
	}

	public List<Item> getTweets(){
		return tweets;
	}

	@Override
    public boolean equals(Object other){
        if(other == this)
            return true;
        if( !(other instanceof SearchResult) )
            return false;

        SearchResult right = (SearchResult) other;
        return keyword.equals(right.keyword) && tweets.equals(right.tweets);
    }

	@Override
    public int hashCode() {
        return Objects.hash(keyword, tweets);
    }
}