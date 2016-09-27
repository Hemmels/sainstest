package runtime;

import java.util.ArrayList;

import org.jsoup.nodes.Document;

import pojo.RipeFruit;

public class ScreenScraper {
	
	private static final String URL_TEST_RIPE_PAGE = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	private static final String URL_LIVE_RIPE_PAGE = "http://www.sainsburys.co.uk/shop/gb/groceries/fruit-veg/ripe---ready";
	
	public Document pageData;
	private float costTotal = 0.0f;
	
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

	public String scrapeFrontPage(){
		ArrayList<RipeFruit> fruitItems = null;
		ArrayList<String> fruitUrls = new ArrayList<String>();
		
		// Scrape page's html
		fruitUrls = getFruitUrls(pageData);
		fruitItems = new ArrayList<RipeFruit>(fruitUrls.size());
		
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
	
	/**
	 * Simply pulls a list of URLs for fruits on the page
	 * @param pageData
	 * @return
	 */
	private ArrayList<String> getFruitUrls(Document pageData){
		return new ArrayList<String>();
	}


	/**
	 * "Visits" a fruit webpage and pulls back a pojo.
	 * @param pageData
	 * @return
	 */
	private RipeFruit scrapeFruitPage(String url){
		Document fruitPage = goToPage(url);
		Object[] fruitData = processPage(fruitPage);
		new RipeFruit(fruitData);
		return null;
	}
	
	public RipeFruit testScrapeFruitPage(String url){
		return scrapeFruitPage(url);
	}

	private Object[] processPage(Document fruitPage) {
		return new Object[]{"title", "size", 0.01, "desc"};
	}
	
	public Object[] testProcessPage(Document fruitPage){
		return processPage(null);
	}
	
	public String getTestUrl(){
		return URL_TEST_RIPE_PAGE;
	}
	
	public float getCostTotal(){
		return costTotal;
	}

}
