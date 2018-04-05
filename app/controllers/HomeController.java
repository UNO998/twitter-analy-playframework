package controllers;

import static akka.pattern.PatternsCS.ask;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.actor.ActorRef;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import play.libs.concurrent.HttpExecutionContext;
import play.data.FormFactory;

import views.html.*;
import javax.inject.Inject;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;

import lyc.Item;
import actors.TwitterActor;
import actors.UserActor;
import actors.Message;


public class HomeController extends Controller{

	@Inject private ActorSystem actorSystem;
	@Inject private Materializer materializer;
	@Inject private HttpExecutionContext httpExecutionContext;
	private final Timeout timeout = new Timeout(2, TimeUnit.SECONDS);

	private ActorRef TwitterActor;
	private final Form<WidgetData> form;
	private CompletableFuture<List<Item>> tweets;
	

	@Inject public HomeController(ActorSystem system, FormFactory formFactory){
		//twitterActor = system.actorOf(TwitterActor.getProps(), "TimeActor");
		// the path of twitterActor is "/user/twitterActor"
		twitterActor = actorSystem.actorOf(TwitterActor.getProps(), "twitterActor");
		form = formFactory.form(WidgetData.class);

		// initialization
		this.tweets = CompletableFuture.supplyAsync(() -> new ArrayList<Item>());   
	} 



	public CompletionStage<Result> index() {
        return tweets.thenApplyAsync(results -> 
            ok(views.html.index.render(form, asScala(results))), httpExecutionContext.current()
            );
    }




    /**
     * Reponse of 'Post /search' request.
     * This method will get the data of the input box and search tweets according to the keyword asynchronously
     *
     * @return a future oject of the main page. In other word, it call the index() method agian.
     */
    public CompletionStage<Result> search() {
        final Form<WidgetData> boundForm = form.bindFromRequest();
		
		try{
			final WidgetData data = boundForm.get();
			Message.Keyword keyword = new Message.Keyword( data.getKeyword() );

			// throw an AskTimeoutException exception if timeout
			tweets = ask(twitterActor, keyword, timeout)
				.thenApply(object -> (List<Item>) object);

        	return CompletableFuture.completedFuture(redirect(routes.WidgetController.index()));
		}catch(Exception ex){
			// catch exception if the form is empty
			play.Logger.ALogger logger = play.Logger.of(getClass());
        	logger.error("missing input");

			return tweets.thenApplyAsync(results -> 
				ok(views.html.index.render(form, asScala(results))), httpExecutionContext.current()
			);
		}

    }

    public WebSocket createWebSocket(){
    	return WebSocket.Json.accept(request -> 
    		ActorFlow.actorRef(UserActor::props, actorSystem, materializer))
    }

}