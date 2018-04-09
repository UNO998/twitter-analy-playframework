package actors;


import java.util.List;
import lyc.Item;
import lyc.SearchResult;

import static java.util.Objects.requireNonNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * The class is to define the the format of messages.
 */
public final class Message {

    /**
     * The register message is used to register for a new incoming actor.
     */
    public static final class Register{

    }

    public static final class Clear{

    }

    public static final class Keyword{
        private String keyword;

        /**
         * Constructor
         * @param keyword Searching Twitter keyword
         */
        public Keyword(String keyword){
            this.keyword = requireNonNull(keyword);
        }

        /**
         * Get the searching Twitter keyword
         * @return the TWitter keyword
         */
        public String getKeyword(){
            return keyword;
        }
    }

    /**
     * The class is used to pass the Twitter's user information.
     */
    public static final class User_id{
        private long user_id;

        /**
         * Constructor
         * @param user_id Twitter's user information
         */
        public User_id(long user_id){
            this.user_id = user_id;
        }

        /**
         * Get User_id
         * @return Twitter's user id
         */
        public long getUser_id(){
            return user_id;
        }
    }

    /**
     * This class is to pass the tweet information.
     */
    public static final class Tweet {
        private Item tweet;

        /**
         * Constructor
         * @param tweet Tweet information
         */
        public Tweet(Item tweet){
            this.tweet = requireNonNull(tweet);
        }
    }

    /**
     * This class is to pass a clock message.
     */
    public static final class Tick{

    }

    /**
     * This class is to pass a update message.
     */
    public static class Update {
        final private CompletableFuture<List<SearchResult>> tweets;

        /**
         * Constructor
         * @param tweets A list of tweets
         */
        public Update(CompletableFuture<List<SearchResult>> tweets){
            this.tweets = requireNonNull(tweets);
        }


        /**
         * Get the list of tweets
         * @return The list of tweets.
         */
        public CompletableFuture<List<SearchResult>> getTweets(){
            return this.tweets;
        }
    }



}
