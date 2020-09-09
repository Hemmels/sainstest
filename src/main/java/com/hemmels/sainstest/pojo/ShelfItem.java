package com.hemmels.sainstest.pojo;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;

/**
 * Used as POJO for holding data about each item on a page
 */
@Data
public class ShelfItem {

	private String title;
	private float kcal_per_100g;
	private BigDecimal unit_price;
	private String description;

	public static ShelfItem fromJson(String json)
	{
		return new Gson().fromJson(json, ShelfItem.class);
	}

	public ShelfItem(Object[] itemData) {
		setTitle((String) itemData[0]);
		setKcal_per_100g((float) itemData[1]);
		setUnit_price(new BigDecimal((String) itemData[2]));
		setDescription((String) itemData[3]);
	}

	public static Type getType()
	{
		return new TypeToken<List<ShelfItem>>() {
		}.getType();
	}
}
