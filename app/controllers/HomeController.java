package controllers;

import static akka.pattern.PatternsCS.ask;
import akka.actor.ActorSystem;
import akka.stream.Materializer;
import akka.actor.ActorRef;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import play.libs.concurrent.HttpExecutionContext;
import play.data.FormFactory;
import play.data.Form;
import static play.libs.Scala.asScala;

import views.html.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import akka.util.Timeout;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import lyc.Item;
import lyc.SearchResult;
import lyc.TwitUserFactory;
import lyc.UserBase;
import actors.TwitterActor;
import actors.UserActor;
import actors.Message;

/**
 * This controller contains the actions to make a websocket connection,
 * initial actor system, define actors.
 */
@Singleton
public class HomeController extends Controller{
	@Inject private ActorSystem actorSystem;
	@Inject private Materializer materializer;
	@Inject private HttpExecutionContext httpExecutionContext;
	private final Timeout timeout = new Timeout(2, TimeUnit.SECONDS);

	private ActorRef twitterActor;
	private final Form<WidgetData> form;
	private CompletableFuture<List<SearchResult>> tweets;

	play.Logger.ALogger logger = play.Logger.of(getClass());


	/**
	 * Constructor
	 * @param formFactory The factory whihch create the controller.
	 * @param twitterActor The actorRef of TwitterActor.
	 */
	@Inject 
	public HomeController(FormFactory formFactory, @Named("twitterActor") ActorRef twitterActor){
		//twitterActor = system.actorOf(TwitterActor.getProps(), "TimeActor");
		// the path of twitterActor is "/user/twitterActor"
		this.twitterActor = twitterActor;
		this.form = formFactory.form(WidgetData.class);

		// initialization
		this.tweets = CompletableFuture.supplyAsync(() -> new ArrayList<SearchResult>());   
	}


	/**
	 * An action that render HTML page.
	 * @return return a CompletionStage object.
	 */
	public CompletionStage<Result> index() {
        return tweets.thenApplyAsync(results -> 
            ok(views.html.index.render(request() ,form, asScala(results))), httpExecutionContext.current()
            );
    }




    /**
     * Response of 'Post /search' request.
     * This method will get the data of the input box and search tweets according to the keyword asynchronously
     *
     * @return a future object of the main page. In other word, it call the index() method again.
     */
    public CompletionStage<Result> search() {
        final Form<WidgetData> boundForm = form.bindFromRequest();
		
		try{
			final WidgetData data = boundForm.get();
			Message.Keyword keyword = new Message.Keyword( data.getKeyword() );

			// throw an AskTimeoutException exception if timeout
			CompletionStage<Object> result = ask(twitterActor, keyword, timeout);

			// ask -> Completable<Object>
			CompletableFuture<CompletableFuture<List<SearchResult>>> tmp = result
				.thenApply(objects -> (CompletableFuture<List<SearchResult>>) objects)
				.toCompletableFuture();
			
			CompletableFuture<List<SearchResult>> newSearchResult = tmp.get();
			tweets = newSearchResult.thenCombine(tweets, (op1, op2) -> {
				op1.addAll(op2);
				return op1;
			});

        	return CompletableFuture.completedFuture(redirect(routes.HomeController.index()));
		}catch(Exception ex){
			// catch exception if the form is empty
			play.Logger.ALogger logger = play.Logger.of(getClass());
        	logger.error("missing input");

			return tweets.thenApplyAsync(results -> 
				ok(views.html.index.render(request(), form, asScala(results))), httpExecutionContext.current()
			);
		}

    }


    /**
     * Refresh the page and clear the keyword.
     *
     * @return redirect to the main page.
     */
    public CompletionStage<Result> clear() {
        	tweets = CompletableFuture.supplyAsync(() -> new ArrayList<SearchResult>());

        	twitterActor.tell(new Message.Clear(), ActorRef.noSender());

        	return CompletableFuture.completedFuture(redirect(routes.HomeController.index()));
    }


    /**
     * Response of `Get /userProfile/:id' request. It will send a Message.User_id to twitter actor to get the user's homeline.
     *
     * @param user_id - the user's id
     * @return a future object of the user profile page rendered by userProfile.scala.html or error page if the id doesn't exist.
     */
    public CompletionStage<Result> userProfile(long user_id){
    		CompletableFuture<List<Item>> result = ask(twitterActor, new Message.User_id(user_id), timeout) 
    													.thenApply(object -> (CompletableFuture<List<Item>>) object )
    													.thenApply(future -> future.join())
    													.toCompletableFuture();
    		UserBase user = TwitUserFactory.getInstance().getUserById(user_id);
			return result.thenApplyAsync(userHomeLine -> 
						ok(views.html.userProfile.render(user, asScala(userHomeLine))), httpExecutionContext.current());

    }

	/**
	 * Response of get request from frontend websocket and create a flow of UserActor
	 * To make a web socket connection.
	 *
	 * @return a flow of UserActor when there is a request
	 */
    public WebSocket ws(){
    	return WebSocket.Json.accept(request -> 
    		ActorFlow.actorRef(UserActor::props, actorSystem, materializer));
    }

}
