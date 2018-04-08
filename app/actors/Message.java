package actors;


import java.util.List;
import lyc.Item;
import lyc.SearchResult;

import static java.util.Objects.requireNonNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public final class Message {

    public static final class Register{

    }

    public static final class Clear{
        
    }

    public static final class Keyword{
        private String keyword;

        /**
         * 
         * @param
         */
        public Keyword(String keyword){
            this.keyword = requireNonNull(keyword);
        }

        /**
         * 
         * @param
         */
        public String getKeyword(){
            return keyword;
        }
    }

    public static final class User_id{
        private long user_id;

        /**
         * 
         * @param
         */
        public User_id(long user_id){
            this.user_id = user_id;
        }

        /**
         * 
         * @param
         */
        public long getUser_id(){
            return user_id;
        }
    }


    public static final class Tweet {
        private Item tweet;

        /**
         * 
         * @param
         */
        public Tweet(Item tweet){
            this.tweet = requireNonNull(tweet);
        }
    }

    public static final class Tick{
        
    }


    public static class Update {
        final private CompletableFuture<List<SearchResult>> tweets;

        /**
         * 
         * @param
         */
        public Update(CompletableFuture<List<SearchResult>> tweets){
            this.tweets = requireNonNull(tweets);
        }


        /**
         * 
         * @param
         */
        public CompletableFuture<List<SearchResult>> getTweets(){
            return this.tweets;
        }
    }



}