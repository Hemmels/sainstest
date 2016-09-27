package sainstest;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

import pojo.RipeFruit;

public class ScreenScraper {
	
	private static final String URL_TEST_RIPE_PAGE = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	private static final String URL_LIVE_RIPE_PAGE = "http://www.sainsburys.co.uk/shop/gb/groceries/fruit-veg/ripe---ready";
	public Document pageData;
	
	public ScreenScraper(int liveSwitch) {
		switch(liveSwitch){
			case 1:{
				pageData = goToPage(URL_LIVE_RIPE_PAGE);
				break;
			}
			default:{
				pageData = goToPage(URL_TEST_RIPE_PAGE);
			}
		}
	}

	public static Document goToPage(String page){
		return null;
	}

	public Object scrapeFrontPage(){
		float costTotal = 0.0f;
		ArrayList<RipeFruit> fruitItems = null;
		String[] fruitUrls;
		
		// Scrape page's html
		fruitUrls = getFruitUrls(pageData);
		fruitItems = new ArrayList<RipeFruit>(fruitUrls.length);
		
		// Create data pojos
		for (String fruitUrl : fruitUrls){
			fruitItems.add(scrapeFruitPage(fruitUrl));
		}
		
		// Sum value of items
		costTotal = getCostTotal(pageData);
		
		// Create output data
		return JsonFormatter.createJsonOutput(fruitItems, costTotal);
	}
	
	private float getCostTotal(Document pageData) {
		return 0;
	}

	private String[] getFruitUrls(Document pageData){
		return null;
	}
	
	private RipeFruit scrapeFruitPage(String url){
		Document fruitPage = goToPage(url);
		Object[] fruitData = processPage(fruitPage);
		new RipeFruit(fruitData);
		return null;
	}

	private Object[] processPage(Document fruitPage) {
		return null;
	}

}
