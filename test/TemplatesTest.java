import models.User;
import org.junit.Test;
import play.data.FormFactory;
import play.test.WithBrowser;
import play.twirl.api.Content;
import play.twirl.api.Html;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static play.test.Helpers.contentAsString;

public class TemplatesTest extends WithBrowser {
    @Inject
    FormFactory formFactory;

//    @Before
//    public void configure(Binder binder){
//
//    }

    @Test
    public void userTemplateTest() {
        User mockedUser = mock(User.class);
        Content html = views.html.user.render(mockedUser);
        assertEquals("text/html", html.contentType());
        assertTrue(contentAsString(html).contains("Recent Activity"));
    }

//    @Test
//    public void textTemplateTest(){
//
//        Form<Twitter> form = formFactory.form(Twitter.class).bindFromRequest();
//
//        List<Actor> mockedActor = mock(List.class);
//        Form<Twitter> mockedForm = mock(Form.class);
//
//        Content html = views.html.text.render(mockedActor ,mockedForm);
//        assertEquals("text/html", html.contentType());
//    }

    @Test
    public void layoutTemplateTest(){
        Content html = views.html.layout.render(new String(), new Html(""));
        assertEquals("text/html", html.contentType());
    }
}
