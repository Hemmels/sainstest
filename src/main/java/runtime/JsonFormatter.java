package runtime;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import pojo.RipeFruit;

public abstract class JsonFormatter {

	public static String createJsonOutput(ArrayList<RipeFruit> fruits, float total) {
		Gson gson = new Gson();
		JsonObject dataset = new JsonObject();
		dataset.addProperty("results", gson.toJson(fruits, new TypeToken<List<RipeFruit>>() {
		}.getType()));
		dataset.addProperty("total", total);
		// TODO: Pretty printing
		return gson.toJson(dataset);
	}

}
