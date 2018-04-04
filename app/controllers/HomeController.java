package controllers;

import actors.TimeActor;
import actors.UserActor;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.libs.streams.ActorFlow;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */



    @Inject private ActorSystem actorSystem;
    @Inject private Materializer materializer;


    @Inject public HomeController(ActorSystem system) {
        system.actorOf(TimeActor.getProps(), "timeActor");
    }





    public Result time() {

        return ok(time.render(request()));
    }

    public WebSocket ws() {
        return WebSocket.Json.accept(request -> ActorFlow.actorRef(UserActor::props, actorSystem, materializer));
    }

}
