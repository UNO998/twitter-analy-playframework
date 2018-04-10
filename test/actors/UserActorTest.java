package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lyc.Item;
import lyc.SearchResult;
import lyc.TwitUserImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Akka Testkit for UserActor
 */
public class UserActorTest {
    static ActorSystem system;
    static Message.Update tweets;

    /**
     * setting up the system and Message.Update to
     * to send user actor
     */
    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
        List<Item> testItem = Arrays.asList(new Item(new TwitUserImpl(123456, "user_name", "user_screenName"), "text", new Date()));
        List<SearchResult> resultTest = Arrays.asList(new SearchResult("keyword", testItem));
        tweets = new Message.Update(CompletableFuture.supplyAsync(() -> resultTest));
    }


    /**
     * shut down actor system
     */
    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }


    /**
     * testing the function of user actor
     * we don't except message from user actor ,so we send user actor
     * Message Update to test the whole function
     */
    @Test
    public void testIt() {

        new TestKit(system) {{

            final TestKit probe = new TestKit(system);
            final Props props = Props.create(UserActor.class, probe.getRef());

            final ActorRef subject = system.actorOf(props);

            // the run() method needs to finish within 3 seconds
            within(duration("3 seconds"), () -> {
                subject.tell(tweets, probe.getRef());
                expectNoMsg();
                return null;
            });
        }};
    }
}
