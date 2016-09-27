package test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.CoreMatchers;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import pojo.RipeFruit;
import runtime.ScreenScraper;

public class ScreenscraperTest {
	
	private static String URL_FRUIT_TESTPAGE = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/"
			+ "2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html";
	
	private ScreenScraper screenScraper;
	
	@Before
	public void init(){
		// Go to test site and populate object with data
		screenScraper = new ScreenScraper(0);
	}

	@Test
	public void testGoToPage() {
		assertThat(ScreenScraper.goToPage(screenScraper.getTestUrl()), CoreMatchers.instanceOf(Document.class));
	}

	@Test
	public void testScrapeFrontPage() {
		String output = screenScraper.scrapeFrontPage();
		
		// String checks
		assertThat(output, CoreMatchers.instanceOf(String.class));
		assertTrue(output.length() > 0);
		assertTrue(output.contains("results") && output.contains("total"));
		
		// Is it Json?
		Gson gson = new Gson();
		JsonObject decoded = null;
		try{
			decoded = gson.fromJson(output, JsonObject.class);
		}
		catch (JsonSyntaxException e){
			e.printStackTrace();
		}
		assertTrue(decoded != null);
		
		// Does it make sense?
		assertTrue(decoded.get("results").getAsJsonArray().size() > 0);
		assertTrue(decoded.get("total").getAsFloat() > 0.01);
	}

	@Test
	public void testGetCostTotal() {
		assertTrue(screenScraper.getCostTotal() > 0.01);
	}

	@Test
	public void testScrapeFruitPage() {
		assertThat(screenScraper.testScrapeFruitPage(URL_FRUIT_TESTPAGE), CoreMatchers.instanceOf(RipeFruit.class));
	}
}
