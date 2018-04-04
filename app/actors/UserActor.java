package actors;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.oauth.OAuth;
import play.libs.ws.WSClient;

import java.time.LocalDateTime;


public final class UserActor extends AbstractActor {
    private final ActorRef ws;
    public static String key;
    public static WSClient wsc;
    public static String url;
    public static OAuth.OAuthCalculator oAuthCalculator;

    @Override
    public Receive createReceive() {


        return     receiveBuilder().match(TimeMessage.class, this::sendTime)


                .build();
    }

    @Override
    public void preStart() {


        context().actorSelection("/user/timeActor/")
                .tell(new TimeActor.RegisterMsg(), self());

    }


    private void sendTime(TimeMessage msg) {
        System.out.println(msg.time);
        final ObjectNode response = Json.newObject();

        response.put("time", msg.time);
        ws.tell(response, self());
    }

    static public class TimeMessage {
        public final String time;
        public TimeMessage(String time) {
            this.time = time;
        }
    }


    public UserActor(final ActorRef wsOut) {

        ws =  wsOut;
    }

    public static Props props(final ActorRef wsout) {
        return Props.create(UserActor.class, wsout);
    }

}