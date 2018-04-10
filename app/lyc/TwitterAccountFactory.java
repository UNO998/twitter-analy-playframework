package lyc;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class is a implementation of AccountFactory based on TwitterAccount.
 */
public class TwitterAccountFactory implements AccountFactory {
    /**
     * Create the twitter account for connection.
     * @param auths The authentication message for this account
     * @return A valid TwitConnection object.
     * @exception TwitterException Catch the exception if verify failed.
     */
    @Override
    public TwitConnection createAccount(String []auths) {
        if(auths.length != 4)
            return null;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(auths[0])
                .setOAuthConsumerSecret(auths[1])
                .setOAuthAccessToken(auths[2])
                .setOAuthAccessTokenSecret(auths[3]);

        TwitterFactory t_factory = new TwitterFactory(cb.build());
        Twitter twitter = t_factory.getInstance();

        try {
            twitter.verifyCredentials();
            return new TwitConnection(twitter);
        }
        catch(TwitterException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
