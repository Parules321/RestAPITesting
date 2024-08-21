package com.RestfulBookerApiTesting;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.CreateBookingRequest;
import utils.TokenGenerator;

public class RestfulBookerAPIWithAuth {

	Faker faker = new Faker();
	Response response ;
	CreateBookingRequest createBookingRequest = new CreateBookingRequest();

	@BeforeMethod
	public void setup() {
		// Set the base url
		baseURI = "https://restful-booker.herokuapp.com/";
		basePath ="booking";
	}
	
//	@Test
//	public void testUpdateBooking() {
//		
//		String token = TokenGenerator.generateToken();
//		System.out.println(token);
//		
//		//Create a booking and extract Booking id
//		String bookingid = createBooking();
//		
//		createBookingRequest.setFirstname("Parul");
//		
//		//Call the update API - updateBookingResponseSchema.json
//		 given().basePath("booking").header("Content-Type","application/json").header("Accept","application/json").header("Cookie","token="+token)
//					.pathParam("id", bookingid).body(createBookingRequest).log().all().when().put("/{id}").then().log().all().statusCode(200).
//					body("firstname", equalTo(createBookingRequest.getFirstname())).body("id",isA(Integer.class));
//		
//		
//	}
//	
//	
	public String createBooking() {
		
		String bookingid =null;
		
		
		
		createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice(111);
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates("2018-01-01", "2018-01-05"));
		createBookingRequest.setAdditionalneeds("breakfast");
		
	 	 response = given().basePath("booking").header("Content-Type","application/json").header("Accept","application/json").body(createBookingRequest).log().all().when().post();
	 	
	 	 System.out.println("Create booking response : "+response.asPrettyString());
	 	 
	 	bookingid = response.getBody().jsonPath().getString("bookingid");
	 			
		return bookingid;
	}
}