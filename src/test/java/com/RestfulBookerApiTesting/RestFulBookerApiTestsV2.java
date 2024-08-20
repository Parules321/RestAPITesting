package com.RestfulBookerApiTesting;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.core.IsAnything;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.CreateBookingRequest;

/*
 * Given i have the baseurl, headers, params, request body
When i invoke the api
Then i should get an response and i will verify the status and response body
 */

public class RestFulBookerApiTestsV2 {

	Faker faker = new Faker();

	@BeforeClass
	public void setup() {
		// Set the base url
		baseURI = "https://restful-booker.herokuapp.com/";
	}

	@Test
	public void testGetbooking() {

		given().basePath("booking").log().all().when().get().then().log().all().statusCode(200);
	}

	@Test
	public void testGetBookingWithQueryParams() {

		given().basePath("booking").queryParam("firstname", "Jake").param("lastname", "Smith").log().all().when().get()
				.then().statusCode(200).log().all();
	}

	@Test
	public void testGetBookingWithPathParams() {

		given().basePath("booking").pathParam("id", 1668).log().all().when().get("/{id}").then().log().all()
				.statusCode(200);
	}

	@Test
	public void testCreateBooking() {

		String requestBody = "{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
				+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n" + "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \"2018-01-01\",\r\n" + "        \"checkout\" : \"2019-01-01\"\r\n"
				+ "    },\r\n" + "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}";

		
		given().basePath("booking").header("Content-Type", "application/json").body(requestBody).log().all().when()
				.post().then().log().all().statusCode(200);
	}

	@Test
	public void testCreateBookingUsingPojo() {

		CreateBookingRequest createBookingRequest = new CreateBookingRequest();
		createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice(111);
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates("2018-01-01", "2018-01-05"));
		createBookingRequest.setAdditionalneeds("breakfast");
		

	//System.out.println(createBookingRequest.toString());
		
		
		
		given().basePath("booking").header("Content-Type", "application/json").body(createBookingRequest)
				.when().post().then().statusCode(200);
	}
	
	@Test
	public void testResponseBodyValidationsUsingHamcrestMatchers() {
		
		CreateBookingRequest createBookingRequest = new CreateBookingRequest();
		
		createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice(111);
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates("2018-01-01", "2018-01-05"));
		createBookingRequest.setAdditionalneeds("breakfast");

		//System.out.println(createBookingRequest.toString());
		
		
		
		given().basePath("booking").header("Content-Type", "application/json").body(createBookingRequest)
				.when().post().then().statusCode(200).body("bookingid", isA(Integer.class)).body("booking.firstname",equalTo(createBookingRequest.getFirstname()))
				.body("booking.lastname", equalTo(createBookingRequest.getLastname())).body("booking.totalprice", greaterThan(100))
				.body("booking.bookingdates.checkin", equalTo(createBookingRequest.getBookingDates().getCheckin())).time(lessThanOrEqualTo(1000L)).body("booking.firstname", isA(String.class));
	}
	
	
	
	@Test
	public void testResponseBodyValidationsUsingJsonpath() {
		
		CreateBookingRequest createBookingRequest = new CreateBookingRequest();
		
		createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice(111);
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates("2018-01-01", "2018-01-05"));
		createBookingRequest.setAdditionalneeds("breakfast");

		System.out.println(createBookingRequest.toString());
		
		Response response =null;
		
		response = given().basePath("booking").header("Content-Type", "application/json").body(createBookingRequest).log().all()
				.when().post();
		
		//Validating response body using Jsonpath
		Assert.assertNotNull(response.getBody().jsonPath().getString("bookingid"));
		Object bookingid = response.getBody().jsonPath().get("bookingid");
		Assert.assertEquals(response.statusCode(), 200);
	
		Assert.assertTrue(bookingid instanceof Integer);
		
		Assert.assertEquals(response.getBody().jsonPath().getString("booking.firstname"), createBookingRequest.getFirstname());
		Assert.assertEquals(response.getBody().jsonPath().getString("booking.lastname"), createBookingRequest.getLastname());
		Assert.assertEquals(response.getBody().jsonPath().getDouble("booking.totalprice"), createBookingRequest.getPrice());
		Assert.assertTrue(response.getBody().jsonPath().getBoolean("booking.depositpaid"));
		Assert.assertEquals(response.getBody().jsonPath().getString("booking.additionalneeds"), createBookingRequest.getAdditionalneeds());
	}
	
	@Test
	public void testCreateBookingSchema() {
		String schemaName = "createBookingResponseSchema.json";
		CreateBookingRequest createBookingRequest = new CreateBookingRequest();
		
		createBookingRequest.setFirstname(faker.name().firstName());
		createBookingRequest.setLastname(faker.name().lastName());
		createBookingRequest.setPrice(111);
		createBookingRequest.setDepositpaid(true);
		createBookingRequest.setBookingDates(new BookingDates("2018-01-01", "2018-01-05"));
		createBookingRequest.setAdditionalneeds("breakfast");
//	File file = new File("src/test/resources/schemas/createBookingResponseSchema.json");
//		
//	given().basePath("booking").header("Content-Type", "application/json").body(createBookingRequest).log().all()
//		.when().post().then().log().all().body(JsonSchemaValidator.matchesJsonSchema(file));
		
	}
}