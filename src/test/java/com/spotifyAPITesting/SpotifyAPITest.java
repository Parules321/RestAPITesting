package com.spotifyAPITesting;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.PropertyUtil;

public class SpotifyAPITest {

	@BeforeMethod
	public void setup() {
		baseURI = "https://api.spotify.com";
		basePath = "v1/artists";

	}
//only difference b/w given and when is syntatctical - they both return request specification.
	//http method returns the response and then() and after that all methods return validatable reponse
	@Test
	public void testSpotifyAPI() {
		String accessToken = SpotifyTokenGenerator.generateAccessToken();
		accessToken = "Bearer " + accessToken;

		String artistId = PropertyUtil.getProperty("src/test/resources/config.properties", "artistId");

		given().pathParam("id", artistId).header("Authorization", accessToken).log().all().when().get("/{id}").then().log().all()
				.statusCode(200).time(lessThanOrEqualTo(300L));

	}
}