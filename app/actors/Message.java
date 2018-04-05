package actors;


import java.util.List;
import lyc.Item;

import static java.util.Objects.requireNonNull;

public final class Messages {

    public static final class Register{

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

        public String getKeyword(){
            return keyword;
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
        final private List<Item> tweets;

        /**
         * 
         * @param
         */
        public Update(List<Item> tweets){
            this.tweets = requireNonNull(tweets);
        }


        /**
         * 
         * @param
         */
        public List<Item> getTweets(){
            return this.tweets;
        }
    }



}