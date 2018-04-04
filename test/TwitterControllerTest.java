import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class TwitterControllerTest extends WithApplication {

    @Override
    protected Application provideApplication(){
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testGetPage() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/twitter/getPage");


        Result result = route(app, request);


        assertEquals(OK, result.status());
        assertTrue(Helpers.contentAsString(result).contains("Home"));
        assertTrue(Helpers.contentAsString(result).contains("Refresh"));
        //assertTrue(Helpers.contentAsString(result).contains("Search"));

    }


    @Test
    public void testRefresh() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/twitter/refresh");
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }


    @Test
    public void testSave() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(POST)
                .bodyForm(ImmutableMap.of("hashtag", "WingCueng_Ray"))
                .uri("/twitter/save");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());


        request = Helpers.fakeRequest()
                .method(GET)
                .uri("/twitter/getPage");
        result = route(app, request);
        assertEquals(OK, result.status());
    }


    @Test
    public void testUserInfo(){
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/twitter/getPage/2965074672");

        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
//        assertTrue(Helpers.contentAsString(result).contains("test 31111!"));
//        assertTrue(Helpers.contentAsString(result).contains("hello world"));
    }


    @Test
    public void testAuth() {
        Http.RequestBuilder request = Helpers.fakeRequest()
                .method(GET)
                .uri("/twitter/getPage/2965074672");
        Result result = route(app, request);
        assertEquals(SEE_OTHER, result.status());
    }
}


