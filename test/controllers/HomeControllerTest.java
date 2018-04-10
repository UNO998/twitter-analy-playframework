package controllers;

import actors.TwitterActor;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lyc.AccountFactory;
import lyc.TwitUserFactory;
import lyc.TwitterAccountFactory;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.akka.AkkaGuiceSupport;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;


public class HomeControllerTest extends WithApplication {
    public static class Module extends AbstractModule  implements AkkaGuiceSupport{
        @Override
        protected void configure() {
            bind(AccountFactory.class)
                    .annotatedWith(Names.named("twitterFactory"))
                    .to(TwitterAccountFactory.class);

            bindActor(TwitterActor.class, "twitteraActor");
            //bindActorFactory(UserActor.class, UserActor.Factory.class);
        }

    }
    @Override
    protected Application provideApplication(){

//        return new GuiceApplicationBuilder().load(new HomeControllerTest.Module()).build();
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testSearch() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/widgets");


        Result result = route(app, request);


        assertEquals(OK, result.status());
//        assertTrue(Helpers.contentAsString(result).contains("Home"));
//        assertTrue(Helpers.contentAsString(result).contains("clear"));
        //assertTrue(Helpers.contentAsString(result).contains("Search"));

    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/");


        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertTrue(Helpers.contentAsString(result).contains("Home"));
        assertTrue(Helpers.contentAsString(result).contains("clear"));
    }

    @Test
    public void testClear() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/clear");
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }

//      @Test
//    public void testUserProfile() {
//        TwitUserFactory.getInstance().getOrCreateUser(155150886, "asd", "asdas");
//        Http.RequestBuilder request = Helpers.fakeRequest()
//                .method(GET)
//                .uri("/userProfile/155150886");
//
//        Result result = route(app, request);
//        assertEquals(SEE_OTHER, result.status());
//    }

}