package utils;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

import com.google.gson.JsonObject;

import io.restassured.response.Response;

public class TokenGenerator {

	public static String generateToken() {
		String token=null;
		baseURI = "https://restful-booker.herokuapp.com/";
		basePath ="auth";
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("username", PropertyUtil.getProperty("src/test/resources/config.properties", "user"));
		jsonObject.addProperty("password",  PropertyUtil.getProperty("src/test/resources/config.properties", "password"));
		Response response = given().header("Content-Type","application/json").body(jsonObject).log().all().
		when().post();
		
		token = response.getBody().jsonPath().getString("token");
		
		return token;
	}
}