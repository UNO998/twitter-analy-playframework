package actors;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;

public class TimeActor extends AbstractActorWithTimers{

    Set<ActorRef> userActors;

    public static Props getProps(){
        return Props.create(TimeActor.class);
    }

    private static final class Tick{

    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RegisterMsg.class, msg -> userActors.add(sender()))
                .match(Tick.class, msg -> notifyClients())
                .build();
    }



    private void notifyClients(){
        UserActor.TimeMessage tMsg = new UserActor.TimeMessage(LocalDateTime.now().toString());
        userActors.forEach(ar -> ar.tell(tMsg, self()));
    }

    @Override
    public void preStart(){
        getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(5, TimeUnit.SECONDS));
        userActors = new HashSet<>();
    }

    static public class RegisterMsg {
    }
}