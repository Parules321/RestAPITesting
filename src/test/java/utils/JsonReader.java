package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonReader {

	public static JsonObject readJson(String filePath) {
		
		JsonObject  jsonObject =null;
		
		Gson gson = new Gson();
		
		FileReader reader;
		try {
			reader = new FileReader(filePath);
			jsonObject = gson.fromJson(reader, JsonObject.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return jsonObject;
	}
}