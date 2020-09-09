package com.hemmels.sainstest.runtime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hemmels.sainstest.exceptions.CouldNotGetDocumentException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ScreenScraper {

	public static final String URL_TEST_DIR = "https://jsainsburyplc.github.io/serverside-test/site/"
			+ "www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/";
	public static final String URL_TEST_PAGE = "berries-cherries-currants6039.html";
	private static final int TIMEOUT_MS = 5000;

	public static final Document goToPage(String page) throws CouldNotGetDocumentException
	{
		int tries = 0;
		while (tries < 4) {
			try {
				return Jsoup.connect(page).timeout(TIMEOUT_MS).userAgent("Mozilla").get();
			} catch (IOException e) {
				log.error("Ow!", e);
			}
			tries++;
		}
		throw new CouldNotGetDocumentException(page);
	}

	public final List<String> scrape()
	{
		return scrapeItemUrls(URL_TEST_DIR + URL_TEST_PAGE);
	}

	public final List<String> scrapeItemUrls(String url)
	{
		Document pageData = null;
		try {
			pageData = goToPage(url);
		} catch (CouldNotGetDocumentException e) {
			log.error("Error thrown visiting {}", url, e);
			return new ArrayList<>();
		}

		// Scrape page's html
		return getItemUrls(pageData);
	}

	/**
	 * Simply pulls a list of URLs for items on the page
	 * 
	 * @param pageData
	 * @return
	 */
	private final List<String> getItemUrls(Document pageData)
	{
		List<String> urls = new ArrayList<>();
		Elements pageProducts = pageData.select(".productInfo");
		for (Element element : pageProducts) {
			String path = element.select("a").attr("href").trim();
			if (path.startsWith("../")) {
				path = URL_TEST_DIR + path;
			}
			urls.add(path);
		}
		return urls;
	}

	public final Document scrapeDocument(String url)
	{
		Document itemPage = null;
		try {
			itemPage = goToPage(url);
		} catch (CouldNotGetDocumentException e) {
			log.error("Failed going to {}", url, e);
		}
		return itemPage;
	}

}
