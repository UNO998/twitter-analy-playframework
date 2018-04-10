import actors.*;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;
import com.google.inject.name.Names;

import lyc.AccountFactory;
import lyc.TwitterAccountFactory;

/**
 * This class extends from AkkaGuiceSupport.
 */
@SuppressWarnings("unused")
public class Module extends AbstractModule implements AkkaGuiceSupport {
	/**
	 * Override the configuration to combine witch the configuration for Twitter.
	 */
    @Override
    protected void configure() {
    	bind(AccountFactory.class)
			.annotatedWith(Names.named("twitterFactory"))
			.to(TwitterAccountFactory.class);
    	bindActor(TwitterActor.class, "twitterActor");
        //bindActor(TwitterActor.class, "twitteraActor");
        //bindActorFactory(UserActor.class, UserActor.Factory.class);
    }
}