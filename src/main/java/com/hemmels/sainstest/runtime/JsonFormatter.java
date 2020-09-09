package com.hemmels.sainstest.runtime;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hemmels.sainstest.pojo.ShelfItem;

public final class JsonFormatter {

	private JsonFormatter() {
		throw new InstantiationError("Cannot contruct this class");
	}

	/**
	 * Converts data objects to json string
	 * 
	 * @param itemData All items found on the page
	 * @param total Sum price of 1 of each item
	 * @return String the output JSON
	 */
	public static String createJsonOutput(List<ShelfItem> itemData, float total)
	{
		Gson gson = new Gson();

		JsonObject resultJson = new JsonObject();
		JsonElement itemJson = gson.toJsonTree(itemData);
		resultJson.add("results", itemJson);

		JsonObject totalElement = new JsonObject();
		totalElement.addProperty("gross", String.format("%.2f", total));
		totalElement.addProperty("vat", String.format("%.2f", total - total / 1.2));
		resultJson.add("total", totalElement);

		// Remove 0 calories items
		return gson.toJson(resultJson).replace(",\"kcal_per_100g\":0.0", "");
	}

}
