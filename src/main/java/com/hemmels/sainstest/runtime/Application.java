package com.hemmels.sainstest.runtime;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.hemmels.sainstest.pojo.ShelfItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

	public static void main(String[] args)
	{
		ScreenScraper screenScraper = new ScreenScraper();

		// Get a list of items on the main page
		List<String> itemUrls = screenScraper.scrape();
		log.debug("There are {} items found on the page", itemUrls.size());

		// Create item list for JSON output
		List<ShelfItem> items = itemUrls.stream().map(screenScraper::scrapeDocument).map(PageParser::getItemInfo)
				.collect(Collectors.toList());
		// Also get the total
		float total = items.stream().map(ShelfItem::getUnit_price).reduce(BigDecimal.ZERO, BigDecimal::add).floatValue();

		// Print output
		new ConsolePrinter().printToConsole(items, total);
	}

}
