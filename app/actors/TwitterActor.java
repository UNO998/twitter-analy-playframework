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
import java.util.stream.Stream;
import java.util.stream.Collectors;

import akka.actor.ActorRef;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
import akka.actor.AbstractActorWithTimers;

import javax.inject.Inject;
import javax.inject.Named;

import lyc.*;

/**
 * create TwitterActor, create auths to Twitter, set timmer, search the tweets
 */
public class TwitterActor extends AbstractActorWithTimers{
	private final CompletableFuture<Connection> twitter;
	private final Set<ActorRef> userActors;

	private final HashSet<String> keywords;
	private final HashMap<String, List<Item>> history;
	private final int limit = 10;

	play.Logger.ALogger logger = play.Logger.of(getClass());


	/**
	 * constructor, config auths and install parameter
	 * @param factory
	 */
	@Inject
	public TwitterActor(@Named("twitterFactory") AccountFactory factory){
		userActors = new HashSet<>();
		keywords = new HashSet<>();
		history = new HashMap<>();

		String []auths = new String[]{
				"M0YLKTvUOn94ppgkndCtNOzg8",
				"SLyd4eDSj7gQf9go2VVzTU1WQ8fxD78nngbDeUGF1z3h74Feak",
				"4035781342-sTvTau17c3v358hkPhMkFymA7EPjHL5GNgUfFFK",
				"riwyeob5JPgJDkxFqF7m8Ec7DFEq6aIDYkdzOMLswBkh1"
		};


		this.twitter = CompletableFuture.supplyAsync( () -> factory.createAccount(auths) );
	}


	/**
	 *
	 * configuration and set timmer
	 */
	@Override
    public void preStart(){
        getTimers().startPeriodicTimer("Timer", new Message.Tick(), Duration.create(5, TimeUnit.SECONDS));
    }


	/**
	 * get TwitterActor
	 * @return Create TwitterActor class
	 */
	public static Props getProps(){
		return Props.create(TwitterActor.class);
	}


	/**
	 * install receive, TwitterActor change behavior dependents on message
	 * @return receive
	 */
	@Override
	public Receive createReceive(){
		return receiveBuilder()
			.match(Message.Register.class, msg -> {userActors.add(sender()); })
			.match(Message.Clear.class, msg -> {keywords.clear(); history.clear();})
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
	 * notify the userActor to update the tweets
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
	 * update the old history item
	 * @param now new item list
	 * @param history old item list
	 * @return new list of item
	 */
	private List<Item> getUpdate(List<Item> now, List<Item> history) {
		Stream<Item> combination = Stream.concat(now.stream(), history.stream());

		List<Item> update = combination
			.distinct()
			.sorted((op1, op2) -> op1.getCreated_time().before(op2.getCreated_time())? 1 : -1 )
			.limit(limit)
			.collect(Collectors.toList());


		return update;
	}



}
