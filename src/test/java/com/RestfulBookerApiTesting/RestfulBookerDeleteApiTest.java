package com.RestfulBookerApiTesting;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;

import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.CreateBookingRequest;
import utils.PropertyUtil;

public class RestfulBookerDeleteApiTest {
	Faker faker = new Faker();
	Response response ;
	CreateBookingRequest createBookingRequest = new CreateBookingRequest();
	
	@BeforeMethod
	public void setup() {
		baseURI = "https://restful-booker.herokuapp.com/";
	}
	//when we pass POJO in body, Rest Assured serializes the Java object 
	//createBookingRequest into JSON format, which is then sent as the request body.
	// but jackson can provide further capabilities - sreializing to string, deserializing to java class
	@Test
	public void testDeleteBooking() {
		
		String token = generateTokenForDeleteApi();
		String bookingid = getBookingId();
	
		 given().basePath("booking").log().all().header("Content-Type","application/json").header("Accept","application/json").header("Cookie","token="+token)
					.pathParam("id", bookingid).
					
					log().all().when().delete("/{id}").then().log().all().statusCode(201);		
		
	}
	
	public String getBookingId() {
		
		String bookingid =null;
		 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	    createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice((Double.parseDouble((faker.commerce().price()))));
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates(sdf.format(faker.date().future(1, TimeUnit.DAYS)).toString(), sdf.format(faker.date().future(7, TimeUnit.DAYS)).toString()));
		createBookingRequest.setAdditionalneeds(faker.food().dish());
		
	 	 response = given().basePath("booking").header("Content-Type","application/json").header("Accept","application/json").body(createBookingRequest).log().all().when().post();
	 	
	 	 System.out.println("Booking response generated to extract booking id : "+response.asPrettyString());
	 	 
	 	bookingid = response.getBody().jsonPath().getString("bookingid");
	 			
		return bookingid;
	}
	
	
	public  String generateTokenForDeleteApi() {
		String token=null;		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("username", PropertyUtil.getProperty("src/test/resources/config.properties", "user"));
		jsonObject.addProperty("password",  PropertyUtil.getProperty("src/test/resources/config.properties", "password"));
		response = given().basePath("auth").header("Content-Type","application/json").body(jsonObject).log().all().
		when().post();
		
		token = response.getBody().jsonPath().getString("token");
		
		return token;
	}
}
