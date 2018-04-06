package lyc;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


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


}