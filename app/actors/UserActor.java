package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.oauth.OAuth;
import play.libs.ws.WSClient;

import java.time.LocalDateTime;


public class UserActor extends AbstractActor{
	private final ActorRef ws;

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
		receiveBuilder()
			.match(Message.Update.class, msg -> sendUpdate(msg));
	}



	/**
     * 
     * @param
     */
	private void sendUpdate(Message.Update msg){
		List<Item> newItems = msg.getTweets();

		final ObjectNode reponse = Json.newObject();
		ArrayNode arrayNode = reponse.putArray("Tweets");

		for(Item tweet : newItems){
			object = arrayNode.addObject();
			object.put("user_name", tweet.getUser_name());
			object.put("text", tweet,getText());
			object.put("href", tweet.getUser_link());
		}

		// send Json msg to JavaScript
		ws.tell(reponse, self());	
	}
} 