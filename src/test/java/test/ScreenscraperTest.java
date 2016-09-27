package test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import exceptions.CouldNotGetDocumentException;
import pojo.RipeFruit;
import runtime.ScreenScraper;

public class ScreenscraperTest {

	private static String URL_FRUIT_TESTPAGE = "http://hiring-tests.s3-website-eu-west-1.amazonaws.com/"
			+ "2015_Developer_Scrape/sainsburys-apricot-ripe---ready-320g.html";

	private ScreenScraper screenScraper;

	@Before
	public void init() {
		// Go to test site and populate object with data
		screenScraper = new ScreenScraper();
	}

	@Test
	public void testGoToPage() {
		try {
			assertThat(ScreenScraper.goToPage(screenScraper.getTestUrl()), CoreMatchers.instanceOf(Document.class));
		}
		catch (CouldNotGetDocumentException e) {
			e.printStackTrace();
		}
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
		try {
			decoded = gson.fromJson(output, JsonObject.class);
		}
		catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		assertTrue(decoded != null);

		// Does it make sense as JSON output?
		ArrayList<RipeFruit> list = gson.fromJson(decoded.get("results"), RipeFruit.getType());
		assertTrue(list.size() > 5);
		assertTrue(decoded.get("total").getAsFloat() > 0.01);
	}

	@Test
	public void testGetCostTotal() {
		screenScraper.scrapeFrontPage();
		assertTrue(screenScraper.getCostTotal() > 0.01);
		assertTrue(screenScraper.getCostTotal() < 9999);
	}

	@Test
	public void testScrapeFruitPage() {
		assertThat(screenScraper.testScrapeFruitPage(URL_FRUIT_TESTPAGE), CoreMatchers.instanceOf(RipeFruit.class));
	}
}
