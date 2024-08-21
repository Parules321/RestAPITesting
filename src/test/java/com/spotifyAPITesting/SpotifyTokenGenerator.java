package com.spotifyAPITesting;

import static io.restassured.RestAssured.*;

import java.time.Instant;
import java.util.Base64;

import org.testng.annotations.BeforeMethod;

import io.restassured.response.Response;

public class SpotifyTokenGenerator {

	@BeforeMethod
	public void setup() {
		baseURI = "https://accounts.spotify.com/";
		basePath = "api/token";

	}

	public static String generateAccessToken() {
		
		String accessToken=null;
		int expiryTime=0;
		
		String clientCredentials = "0beb3fb0fa3340768947ee50fd2e10c0:f97ae1455185433ba900d30acd20cc6b";
		
		String encodedCredentials = "Basic "+Base64.getEncoder().encodeToString(clientCredentials.getBytes());
		
		Instant expectedExpiryTime = null;
		
		//The below does not give any null pointer exception as || is short circuiting OR operator 
		//so the second part is not evaluated when the first part is true and 
		//Instant.now() method will not be invoked when expectedExpiryTime is null. 

		
		if(expectedExpiryTime==null||Instant.now().isAfter(expectedExpiryTime)) {
			
		
//		Response response = given().baseUri("https://accounts.spotify.com/").basePath("api/token").header("Authorization",encodedCredentials).header("Content-Type","application/x-www-form-urlencoded")
//		.formParam("grant_type", "client_credentials").log().all().when().post();
		
		Response response = given().baseUri("https://accounts.spotify.com/").basePath("api/token").auth().preemptive().basic("0beb3fb0fa3340768947ee50fd2e10c0","f97ae1455185433ba900d30acd20cc6b").header("Content-Type","application/x-www-form-urlencoded")
				.formParam("grant_type", "client_credentials").log().all().when().post();
		response.then().statusCode(200).log().all();
		
		System.out.println(response.asPrettyString());
		
		//.jsonPath() - Gets a JsonPath view of the response body. 
		//This will let you use the JsonPath syntax to get values from the response.Example: 
		accessToken= response.body().jsonPath().getString("access_token");
		
		
		 expiryTime= response.body().jsonPath().getInt("expires_in");
		
		 
		 expectedExpiryTime = Instant.now().plusSeconds(expiryTime);
		 
		}
		
		return accessToken;
		
	}

}