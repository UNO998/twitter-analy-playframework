package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


import lyc.*;

public class UserActor extends AbstractActor{
	private final ActorRef ws;
	play.Logger.ALogger logger = play.Logger.of(getClass());

	/**
     * 
     * @param
     */
	public UserActor(final ActorRef wsOut){
		this.ws = wsOut;
	}

	/**
     * 
     * @param
     */
	public static Props props(final ActorRef wsOut){
		return Props.create(UserActor.class, wsOut);
	}


	/**
     * Register the userActor into TweetActor
     * @param
     */
	@Override
	public void preStart(){
		context().actorSelection("/user/twitterActor")
			.tell(new Message.Register(), self());
	}


	/**
     * 
     * @param
     */
	@Override
	public Receive createReceive(){
		return receiveBuilder()
			.match(Message.Update.class, msg -> sendUpdate(msg))
			.build();
	}



	/**
     * 
     * @param
     */
	private void sendUpdate(Message.Update msg){
		CompletableFuture<List<SearchResult>> tweets = msg.getTweets();

		
		tweets.thenApply(newItems -> {
			ObjectNode response = Json.newObject();
			ArrayNode arrayNode = response.putArray("updates");

			//logger.error("5 seconds!!!!!!!!!");
			for(SearchResult each : newItems){
				ObjectNode tweetNode = arrayNode.addObject();
				tweetNode.put("keyword", each.getKeyword());

				//logger.error("keyword: " + each.getKeyword());
				ArrayNode tweetsForKey = tweetNode.putArray("tweets");
				for(Item     item : each.getTweets()){
					//logger.error(item.getText());
					ObjectNode tweet = tweetsForKey.addObject();
					tweet.put("user_name", item.getUser_name());
					tweet.put("text", item.getText());
					tweet.put("href", item.getUser_link());
				}

			}
			//logger.error(prettyPrintJsonString(response));

			return response;
		}).thenAccept( response -> ws.tell(response, self()) );
	}


	/**
     * 
     * @param
     */
	private String prettyPrintJsonString(ObjectNode jsonNode) {
    try {
        ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(jsonNode);
    } catch (Exception e) {
        return "Sorry, pretty print didn't work";
    }
}
} 