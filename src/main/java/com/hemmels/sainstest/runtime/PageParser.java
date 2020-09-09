package com.hemmels.sainstest.runtime;

import java.math.BigDecimal;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.hemmels.sainstest.pojo.ShelfItem;

import lombok.extern.slf4j.Slf4j;

/**
 * Uses Jsoup to pull data from given Documents
 */
@Slf4j
public final class PageParser {

	private PageParser() {
		throw new InstantiationError("Cannot contruct this class");
	}

	public static ShelfItem getItemInfo(Document itemPage)
	{
		Object[] itemData = processItemPage(itemPage);
		return new ShelfItem(itemData);
	}

	private static Object[] processItemPage(Document itemPage)
	{
		String title = itemPage.select("h1").get(0).text();
		float kcals = getKcals(itemPage);
		BigDecimal unitPrice = getUnitPrice(itemPage);
		String description = getDescription(itemPage);

		return new Object[]{title, kcals, unitPrice.toPlainString(), description};
	}

	private static String getDescription(Document itemPage)
	{
		String description = "";
		Elements productInfo = itemPage.select("h3.productDataItemHeader:contains(Description) + div");
		if (!productInfo.isEmpty()) {
			description = productInfo.get(0).text();
		} else {
			description = itemPage.select("div.itemTypeGroupContainer.productText div").get(0).text();
		}
		return description;
	}

	private static BigDecimal getUnitPrice(Document itemPage)
	{
		Elements prices = itemPage.select("p.pricePerUnit");
		return new BigDecimal(prices.get(0).textNodes().get(0).text().replace("£", "").trim());
	}

	private static float getKcals(Document itemPage)
	{
		float kcals = 0;
		Elements kcalFields = itemPage.select("table.nutritionTable th:contains(Energy kcal)");
		if (!kcalFields.isEmpty()) {
			try {
				kcals = Float.parseFloat(kcalFields.parents().get(0).select("td:eq(1)").text());
			} catch (NumberFormatException e) {
				log.error("Failed to read calories on page {}", itemPage.location(), e);
			}
		} else {
			kcals = getCaloriesFromEnergyTable(itemPage);
		}
		return kcals;
	}

	private static float getCaloriesFromEnergyTable(Document itemPage)
	{
		float kcals = 0f;

		Elements kcalFields = itemPage.select("table.nutritionTable th:contains(Energy)");
		if (kcalFields.isEmpty()) {
			return kcals;
		} else {
			try {
				kcals = Float.parseFloat(kcalFields.parents().get(1).select("tbody tr:eq(1) td:eq(0)").text().replace("kcal", ""));
			} catch (NumberFormatException e) {
				log.error("Failed to read calories on page {}", itemPage.location(), e);
			}
		}
		return kcals;
	}
}
