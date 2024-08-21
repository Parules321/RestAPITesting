package com.RestfulBookerApiTesting;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;

import utils.JsonReader;

/*
 * Given i have the baseurl, headers, params, request body
When i invoke the api
Then i should get an response and i will verify the status and response body
 */

public class RestFulBookerApiTestsUsingExternalFiles {

	Faker faker = new Faker();

	@BeforeClass
	public void setup() {
		// Set the base url
		baseURI = "https://restful-booker.herokuapp.com/";
	}

	@Test
	public void testCreateBookingUsingExternalFile() {

		String filePath = "src/test/resources/data/createBookingRequest.json";
		
		JsonObject jsonObject = JsonReader.readJson(filePath);
		
		
		given().basePath("booking").header("Content-Type", "application/json").body(jsonObject).log().all()
				.when().post().then().log().all().statusCode(200);
	}
	
//	@Test
//	public void testAPiUsingDataFromExcel() {
//		
//		
//		
//		
//		
//		given().basePath("booking").header("Content-Type", "application/json").body(createBookingRequest).log().all()
//				.when().post().then().log().all().statusCode(200).body("bookingid", isA(Integer.class)).body("booking.firstname",equalTo(createBookingRequest.getFirstname()))
//				.body("booking.lastname", equalTo(createBookingRequest.getLastname())).body("booking.totalprice", greaterThan(100))
//				.body("booking.bookingdates.checkin", equalTo(createBookingRequest.getBookingDates().getCheckin()));
//	}
	
	
}