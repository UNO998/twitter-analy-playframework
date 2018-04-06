import actors.*;
import com.google.inject.AbstractModule;
import play.libs.akka.AkkaGuiceSupport;

@SuppressWarnings("unused")
public class Module extends AbstractModule implements AkkaGuiceSupport {
    @Override
    protected void configure() {
        //bindActor(TwitterActor.class, "twitteraActor");
        //bindActorFactory(UserActor.class, UserActor.Factory.class);
    }
}