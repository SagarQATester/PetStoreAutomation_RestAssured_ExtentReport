package api.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

@Listeners({ExtentReportManager.class})
public class DDTUserTests 
{

	@Test(priority=1,dataProvider = "Data",dataProviderClass = DataProviders.class)
	public void testPostUsers(String id, String username,String fname,String lname,String email,String pwd,String ph)
	{
		User userPayload= new User();
		userPayload.setId(Integer.parseInt(id));
		userPayload.setUsername(username);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(email);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response response=UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("User request posted ..............");
		
	}
	@Test(priority=2,dataProvider = "UserNames",dataProviderClass = DataProviders.class)
	public void testDeleteUsers(String username)
	{
		Response response=UserEndPoints.deleteUser(username);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		System.out.println("User request Deleted ..............");
	}
}
