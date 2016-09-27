package runtime;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import pojo.RipeFruit;

public abstract class JsonFormatter {

	public static String createJsonOutput(List<RipeFruit> fruits, float total) {
		Gson gson = new Gson();
		JsonObject dataset = new JsonObject();
		JsonElement fruitJson = gson.toJsonTree(fruits);
		dataset.add("results", fruitJson);
		dataset.addProperty("total", total);
		return gson.toJson(dataset);
	}

}
