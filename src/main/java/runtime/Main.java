package runtime;

public class Main {

	public static void main(String[] args) {
		boolean validInput = doInputChecks(args);

		if (validInput) {
			String data = new ScreenScraper(Integer.valueOf(args[0])).scrapeFrontPage();
			new ConsolePrinter().printToConsole(data);
		}
	}

	private static boolean doInputChecks(String[] args) {
		boolean valid = false;
		if (args.length == 1 && args[0].equals("0")) {
			return true;
		}
		if (!valid) {
			System.err.println("Error in args");
		}
		return valid;
	}

}
