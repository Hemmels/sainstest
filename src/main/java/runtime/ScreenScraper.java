package runtime;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import exceptions.CouldNotGetDocumentException;
import pojo.RipeFruit;

public class ScreenScraper {

	private static final String URL_TEST_RIPE_PAGE = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	private static final String URL_LIVE_RIPE_PAGE = "http://www.sainsburys.co.uk/shop/gb/groceries/fruit-veg/ripe---ready";
	// 10s limit for timeout attempt at going to webpage.
	private static int INT_TIMEOUT_LIMIT = 10000;

	public Document pageData;
	private float costTotal = 0.0f;

	public ScreenScraper() {
		try {
			pageData = goToPage(URL_TEST_RIPE_PAGE);
		}
		catch (CouldNotGetDocumentException e) {
			Logger.getGlobal().log(Level.SEVERE, "CouldNotGetDocumentException thrown visiting: " + e.getMessage(), e);
			return;
		}
	}

	public static Document goToPage(String page) throws CouldNotGetDocumentException {
		int tries = 0;
		while (tries < 4) {
			try {
				return Jsoup.connect(page).timeout(INT_TIMEOUT_LIMIT).userAgent("Mozilla").get();
			}
			catch (SocketTimeoutException e) {
			}
			catch (IOException e) {
			}
			tries++;
		}
		throw new CouldNotGetDocumentException(page);
	}

	public String scrapeFrontPage() {
		ArrayList<RipeFruit> fruitItems = null;
		ArrayList<String> fruitUrls = new ArrayList<String>();

		// Scrape page's html
		fruitUrls = getFruitUrls(pageData);
		fruitItems = new ArrayList<RipeFruit>(fruitUrls.size());

		// Create data pojos
		for (String fruitUrl : fruitUrls) {
			fruitItems.add(scrapeFruitPage(fruitUrl));
		}

		// Sum value of items
		costTotal = calcCostTotal(fruitItems);

		// Create output data
		return JsonFormatter.createJsonOutput(fruitItems, costTotal);
	}

	private float calcCostTotal(ArrayList<RipeFruit> fruitItems) {
		float runningCost = 0;
		for (RipeFruit rf : fruitItems) {
			runningCost += rf.getUnitPrice();
		}
		return runningCost;
	}

	/**
	 * Simply pulls a list of URLs for fruits on the page
	 * 
	 * @param pageData
	 * @return
	 */
	private ArrayList<String> getFruitUrls(Document pageData) {
		ArrayList<String> urls = new ArrayList<String>();
		Elements pageProducts = pageData.select(".productInfo");
		for (Element element : pageProducts) {
			urls.add(element.select("a").attr("href").trim());
		}
		return urls;
	}

	/**
	 * "Visits" a fruit webpage and pulls back a pojo.
	 * 
	 * @param pageData
	 * @return
	 */
	private RipeFruit scrapeFruitPage(String url) {
		Document fruitPage;
		int pageBytes;
		try {
			fruitPage = goToPage(url);
		}
		catch (CouldNotGetDocumentException e) {
			e.printStackTrace();
			return null;
		}
		pageBytes = fruitPage.outerHtml().getBytes().length;
		Object[] fruitData = processPage(fruitPage, pageBytes);
		return new RipeFruit(fruitData);
	}

	public RipeFruit testScrapeFruitPage(String url) {
		return scrapeFruitPage(url);
	}

	private Object[] processPage(Document fruitPage, int pageBytes) {
		String title;
		float unitPrice;
		String description;

		Elements productInfo = fruitPage.select("h1");
		title = productInfo.get(0).text();

		productInfo = fruitPage.select("p.pricePerUnit");
		unitPrice = Float.parseFloat(productInfo.get(0).textNodes().get(0).text().replaceAll("£", "").trim());

		productInfo = fruitPage.select("h3.productDataItemHeader:contains(Description) + div");
		description = productInfo.get(0).text();

		return new Object[] { title, pageBytes, unitPrice, description };
	}

	public Object[] testProcessPage(Document fruitPage) {
		return processPage(fruitPage, 1);
	}

	public String getTestUrl() {
		return URL_TEST_RIPE_PAGE;
	}

	public float getCostTotal() {
		return costTotal;
	}

}
