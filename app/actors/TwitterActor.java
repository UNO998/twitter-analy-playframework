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

import lyc.*;


public class TwitterActor extends AbstractActorWithTimers{
	private final CompletableFuture<Connection> twitter;
	private final Set<UserACtor> userActors;
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
		update = new ArrayList<>()
		keyword = new String[]{null};

		AccountFactory factory = new TwitterAccountFactory();
        this.twitter = CompletableFuture.supplyAsync( () -> factory.createAccount(auths) );
	}


	/**
     * 
     * @param
     */
	@Override
    public void preStart(){
        getTimers().startPeriodicTimer("Timer", new Tick(), Duration.create(5, TimeUnit.SECONDS));
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
				CompletableFuture<List<Item>> futureItems = twitter.thenAppy( 
					connection -> connection.SearchPost(k, limit) );
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

		CompletableFuture<List<Item>> tweets = twitter.thenAppy(
			(Connection conn) -> conn.SearchPost(keyword[0], limit);
		);
		tweets.thenAppy(tweets -> {
			List<Item> diff = getUpdate(tweets, history);
			history.clear();
			history.addAll(tweets);
			update.clear();
			update.addAll(diff);

			return diff;

		});

		userActors.foreach( user -> user.tell(new Message.Update(diff), self()) );
	}


	/**
     * 
     * @param
     */
	private List<Item> getUpdate(List<Item> now, List<Item> history){
		// TODO
		return List<Item>({now[0]});
	}


	
} 