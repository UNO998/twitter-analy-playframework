package lyc;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.Actor;
import akka.japi.Creator;
import actors.TwitterActor;

import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;
import com.google.inject.name.Names;
import com.google.inject.Provides;
import javax.inject.Named;

public class Module extends AbstractModule {
  @Override 
  protected void configure() {
  	bind(ActorSystem.class).toInstance(ActorSystem.apply());
     bind(AccountFactory.class)
         .annotatedWith(Names.named("twitterFactory"))
         .to(FakeConnectionFactory.class);

  }

   	@Provides @Named("twitterActor")
	ActorRef serviceActorRef(final ActorSystem system, final @Named("twitterFactory") AccountFactory factory) {
		final Props props = Props.create(TwitterActor.class, () -> new TwitterActor(factory){
					@Override
					public void preStart(){
						return;
				    }

				});
		return system.actorOf(props);
	}

}