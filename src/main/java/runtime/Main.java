package runtime;

public class Main {

	public static void main(String[] args) {
		String data = new ScreenScraper().scrapeFrontPage();
		new ConsolePrinter().printToConsole(data);
	}

}
