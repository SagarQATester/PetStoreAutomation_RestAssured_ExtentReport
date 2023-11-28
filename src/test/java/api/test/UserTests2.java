package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;


import api.endpoints.UserEndPoints2;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;

@Listeners({ExtentReportManager.class})

public class UserTests2 {

	User userPayload;
	Faker faker;
	Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging.......");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("***Creating User ***");
		Response response=UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("User request posted ..............");

		
	}
	@Test(priority=2)
	public void testGetUser()
	{
		Response response =UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("User Get request  ..............");

	}
	@Test(priority=3)
	public void testUpdateUser()
	{
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response =UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		testGetUser();
		System.out.println("User request Updated ..............");

	}
	
	@Test(priority=4)
	public void testDeleteUser()
	{
		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		System.out.println("User request Deleted ..............");

	}
}
