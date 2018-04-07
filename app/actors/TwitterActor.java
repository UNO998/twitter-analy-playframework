package actors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
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

	private final HashSet<String> keywords;
	private final HashMap<String, List<Item>> history;
	private final int limit = 10;

	play.Logger.ALogger logger = play.Logger.of(getClass());

	/**
     * 
     * @param
     */
	public TwitterActor(){
		userActors = new HashSet<>();
		keywords = new HashSet<>();
		history = new HashMap<>();

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
			.match(Message.Register.class, msg -> {userActors.add(sender()); })
			.match(Message.Keyword.class, msg -> {
				CompletableFuture<List<SearchResult>> futureItems = twitter.thenApply( connection ->{
					List<SearchResult> newTweets = new ArrayList<SearchResult>();

					String key = msg.getKeyword();
					List<Item> tweets = connection.SearchPost(key, limit);

					keywords.add(key);
					history.put(key, tweets);
					newTweets.add(new SearchResult(key, tweets));

					return newTweets;
				});

				sender().tell(futureItems, self());
			})
			.match(Message.User_id.class, msg -> {
				CompletableFuture<List<Item>> result = twitter.thenApply(connection -> 
															connection.getHomeLineById(msg.getUser_id()));
				sender().tell(result, self());
			})
			.match(Message.Tick.class, msg -> notifyUsers())
			.build();
	}


	/**
     * 
     * @param
     */
	private void notifyUsers(){
		
		CompletableFuture<List<SearchResult>> updateTweets = CompletableFuture.supplyAsync( () -> {
				List<SearchResult> return_val = new ArrayList<SearchResult>();


				logger.error("HashSet size: " + userActors.size());
				for(String each : keywords){
					twitter
						.thenApply( conn -> conn.SearchPost(each, limit))
						.thenAccept(items -> {
							List<Item> now = items;
							List<Item> before = history.get(each);

							List<Item> diff = getUpdate(now, before);

							history.put(each, now);

							return_val.add(new SearchResult(each, diff));

							// ---------------- debug message ----------------------
							/**
					        logger.error("5 seconds...........");
					        logger.error("Keyword: " + each);
					        items.forEach( item -> logger.error(item.getText()) );
					        **/
					        //------------------------------------------------------
						});
				}
				return return_val;
			});


		for(ActorRef user : userActors){
			try{
				user.tell(new Message.Update(updateTweets), self());
			} catch(Exception e){
				return;
			}	
		}		
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