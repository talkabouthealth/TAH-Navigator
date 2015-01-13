import static org.junit.Assert.*;

import java.util.Calendar;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.Response;
import play.mvc.Http.*;

import org.junit.*;

public class A_Registration extends FunctionalTest 
{
		@Test
	    public void testTheHomePage() 
		{
	        Response response = GET("/secure/login");
	        assertStatus(200, response);
	        //add(Calendar.DATE,20)
		}
}