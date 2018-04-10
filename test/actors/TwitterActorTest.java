package lyc;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.AbstractActor;

import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import actors.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.Arrays;

import play.Application;  
import play.inject.guice.GuiceApplicationBuilder;
import static play.inject.Bindings.bind;
import akka.testkit.TestActorRef;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import java.io.PrintWriter;

public class TwitterActorTest{

	private ActorSystem system;

	PrintWriter writer;
	UserBase user1;
    UserBase user2;
    Date date = FakeConnectionFactory.FakeConnection.date;
    Item item1;
    Item item2;

	public TwitterActorTest(){
		user1 = TwitUserFactory.getInstance().getOrCreateUser(123, "mock_user", "mock_user");
		item1 = new Item(user1, "hello world 1", date);
		item2 = new Item(user1, "hello world 2", date);

    	try{
    		writer = new PrintWriter("logs.txt", "UTF-8");
    	}catch(Exception ex){
    		return;
    	}
	}


	@Before
	public void setup(){
		system = ActorSystem.create();

		
	}

	@After
	public void teardown(){
		writer.close();
		TestKit.shutdownActorSystem(system);
		system = null;
	}


	@Test 
	public void testDependencyInjection(){
		// Use Dependency Injection to create TwitterActor (ActorRef)
		Injector injector = Guice.createInjector(new lyc.Module());
		ActorRef twitterActor = injector.getInstance(Key.get(ActorRef.class, Names.named("twitterActor")));

		new TestKit(system) {{
			// why we don't just use the member system ??
			final TestKit probe = new TestKit(system);
			twitterActor.tell(new Message.Register(), getRef());
			expectNoMsg();


			// test Message.Keyword of createReceive()
			final List<SearchResult> expected_sr = new ArrayList<>();
			expected_sr.add(new SearchResult("hello", new ArrayList<Item>(Arrays.asList(item1))));
			twitterActor.tell(new Message.Keyword("hello"), getRef());
			CompletableFuture<Object> future = expectMsgClass(Duration.create(3, TimeUnit.SECONDS), 
									CompletableFuture.class);
			List<SearchResult> recvMsg = future.thenApply(object -> (List<SearchResult>) object).join();

			assertThat(expected_sr, is(recvMsg));

			
			// test Message.User_id of createReceive()
			final List<Item> expected_items = new ArrayList<Item>(Arrays.asList(item1));
			twitterActor.tell(new Message.User_id(123), getRef());
			future = expectMsgClass(Duration.create(3, TimeUnit.SECONDS), 
									CompletableFuture.class);
			List<Item> recvItems = future.thenApply(object -> (List<Item>) object).join();
			assertThat(expected_items, is(recvItems));

			// test Message.Tick of createReceive()
			expected_sr.get(0).getTweets().add(0, item2);
			twitterActor.tell(new Message.Tick(), getRef());
			Message.Update updateMsg = expectMsgClass(Duration.create(3, TimeUnit.SECONDS), 
											Message.Update.class);
			List<SearchResult> recv = updateMsg.getTweets().join();
			assertThat(recv, is(expected_sr));
	
		}};


	}

}