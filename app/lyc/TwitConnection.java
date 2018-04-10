package lyc;

import twitter4j.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * connect twitter search time homeline
 */
public class TwitConnection implements Connection {
    private Twitter twitter;
    private UserBase user;

    /**
     * The constructor of TwitConnection. An instance can only be instanced by the factory
     * @param t the new instance
     */
    protected TwitConnection(Twitter t) {
        twitter = t;
    }

    /**
     * configuration and get current user information
     * @return user information
     */
    @Override
    public UserBase getCurrentUser(){
        if(user != null)
            return user;

        try {
            AccountSettings account = twitter.getAccountSettings();
            User twit_user = twitter.showUser(account.getScreenName());
            this.user = TwitUserFactory.getInstance().getOrCreateUser(twit_user.getId(), twit_user.getName(), twit_user.getScreenName());

            return this.user;
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Search the most recent posts by keyword
     * @param keyword the keyword that you use to search posts
     * @param max_cnt maximum number of posts returned
     * @return
     */
    @Override
    public List<Item> SearchPost(String keyword, int max_cnt) {
        final int finalMax_cnt;

        ArrayList<Item> results = new ArrayList<>();
        Query query = new Query(keyword);
        try {
            QueryResult q_result = twitter.search(query);
            List<Status> statuses = q_result.getTweets();

            int i = 0;
            for (Status status : statuses) {
                UserBase user = TwitUserFactory.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText(), status.getCreatedAt());

                results.add(result);
                if (++i == max_cnt)
                    break;
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }

    /**
     * config and search tweets
     * @return homeline list
     * @exception TwitterException
     */
    @Override
    public List<Item> getSelfHomeLine() {
        ArrayList<Item> results = new ArrayList<>();

        try {
            List<Status> statues = twitter.getHomeTimeline();
            for (Status status : statues) {
                UserBase user = TwitUserFactory.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText(), status.getCreatedAt());
                results.add(result);
            }

        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }

    /**
     * find search tweets list
     * @param keyword the keyword that you use to search posts
     * @return
     */
    @Override
    public List<Item> SearchPost(String keyword) {
        return SearchPost(keyword, -1);
    }

    /**
     * use user id to search twitter homeline
     * @param user_id
     * @return home line list
     */
    @Override
    public List<Item> getHomeLineById(long user_id) {
        ArrayList<Item> results = new ArrayList<>();

        try {
            List<Status> statues = twitter.getUserTimeline(user_id);
            for (Status status : statues) {
                UserBase user = TwitUserFactory.getInstance().getOrCreateUser(status.getUser().getId(), status.getUser().getName(), status.getUser().getScreenName());
                Item result = new Item(user, status.getText(), status.getCreatedAt());
                results.add(result);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }

        return results;
    }


}
