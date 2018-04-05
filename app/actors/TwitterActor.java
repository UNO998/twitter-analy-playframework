package actors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;

import lyc.*;


public class TwitterActor extends AbstractActorWithTimers{
	private final CompletableFuture<Connection> twitter;
	private final Set<ActorRef> userActors;
	private final List<Item> history;
	private final List<Item> update;

	private final String[] keyword;
	private final int limit = 10;

	/**
     * 
     * @param
     */
	public TwitterActor(){
		userActors = new HashSet<>();
		history = new ArrayList<>();
		update = new ArrayList<>();
		keyword = new String[]{null};

		String []auths = new String[]{
                "WUZeAUiJyJFySY2I5C7oTkaRB",
                "QcmbvjQrSxLscxtZP6PndCYVEXxgBOoZ5g8ryvJLBYAmDTtrPx",
                "2965074672-HcndnMSZkdDKNqF1vqoERR1nynKLnKKqhMovkw4",
                "mue5UQ1QWSwGWDgf1lDTnrSeLJFVJLZltQxdyL34u0C0a"
        };
		AccountFactory factory = new TwitterAccountFactory();
        this.twitter = CompletableFuture.supplyAsync( () -> factory.createAccount(auths) );
	}


	/**
     * 
     * @param
     */
	@Override
    public void preStart(){
        getTimers().startPeriodicTimer("Timer", new Message.Tick(), Duration.create(5, TimeUnit.SECONDS));
    }


	/**
     * 
     * @param
     */
	public static Props getProps(){
		return Props.create(TwitterActor.class);
	}


	/**
     * 
     * @param
     */
	@Override
	public Receive createReceive(){
		return receiveBuilder()
			.match(Message.Register.class, msg -> userActors.add(sender()))

			// TODO: cannot modify keyword in lambda
			// need to return a List<Item>
			.match(Message.Keyword.class, msg -> {
				String k = msg.getKeyword();
				keyword[0] = k;		// !! notice: may have multi-thread bugs here
				CompletableFuture<List<Item>> futureItems = twitter.thenApply( 
					connection -> connection.SearchPost(k, limit) );

				// TODO: change message to CompletableStage type
				sender().tell(futureItems.get(), self());
			})
			.match(Message.Tick.class, msg -> notifyUsers())
			.build();
	}


	/**
     * 
     * @param
     */
	private void notifyUsers(){
		if(keyword[0] == null)
			return;

		CompletableFuture<List<Item>> tweets = twitter.thenApply(
			(Connection conn) -> conn.SearchPost(keyword[0], limit));
		CompletableFuture<List<Item>> newTweets = tweets.thenApply(now -> {
			List<Item> diff = getUpdate(now, history);
			history.clear();
			history.addAll(now);
			update.clear();
			update.addAll(diff);

			return diff;

		});

		// ---------------- debug message ----------------------
		play.Logger.ALogger logger = play.Logger.of(getClass());
        logger.error("5 seconds...........");
        newTweets.thenAccept(now -> {
        	now.forEach( 
        		each -> logger.error(each.getText()) 
        	);
        });
        //------------------------------------------------------

		userActors.forEach( user -> {
			try{
				// TODO: change message to CompletableStage type
				user.tell(new Message.Update(newTweets.get()), self());
			} catch(Exception e){
				return;
			}		
		});


		
	}


	/**
     * 
     * @param
     */
	private List<Item> getUpdate(List<Item> now, List<Item> history){
		// TODO
		return now;
	}


	
} 