//import models.Actor;
//import models.Twitter;
//import models.User;
//import org.junit.Before;
//import org.junit.Test;
//import play.data.Form;
//import play.data.FormFactory;
//import play.libs.concurrent.HttpExecutionContext;
//import play.libs.oauth.OAuth;
//import play.libs.ws.WSClient;
//import play.mvc.Result;
//
//import javax.inject.Inject;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//import static play.mvc.Http.Status.OK;
//import static play.mvc.Results.redirect;
//
//
//public class ControllerTest {
//    public HttpExecutionContext httpExecutionContext;
//
//
//    @Inject
//    FormFactory formFactory;
//
//    static final OAuth.ConsumerKey KEY = new OAuth.ConsumerKey("M0YLKTvUOn94ppgkndCtNOzg8", "SLyd4eDSj7gQf9go2VVzTU1WQ8fxD78nngbDeUGF1z3h74Feak");
//
//    public static final OAuth.ServiceInfo SERVICE_INFO =
//            new OAuth.ServiceInfo("https://api.twitter.com/oauth/request_token",
//                    "https://api.twitter.com/oauth/access_token",
//                    "https://api.twitter.com/oauth/authorize",
//                    KEY);
//
//    public static final OAuth TWITTER = new OAuth(SERVICE_INFO);
//    public WSClient ws;
//    public String hashtag = "";
//    public String strUrl = "https://api.twitter.com/1.1/search/tweets.json?q=%20";
//    public List<Actor> actors = new LinkedList<>();
//    public Map<String, User> users = new HashMap<>();
//
//    @Before
//    public void startApp() {
//    }
//
//    //Test save
//    @Test
//    public void testSave() {
//        Form<Twitter> TitterForm = formFactory.form(Twitter.class).bindFromRequest();
//        Twitter twitter = TitterForm.get();
//        this.hashtag = twitter.hashtag;
//        Result result = redirect(controllers.routes.TwitterController.getPage());
//        assertEquals(OK, result.status());
//        assertEquals("text/plain", result.contentType().get());
//        assertEquals("utf-8", result.charset().get());
//    }
//
//
//}