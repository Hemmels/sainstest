package runtime;

public class Main {

	public static void main(String[] args) {
		boolean validInput = doInputChecks(args);
		
		if (validInput){
			Object data = new ScreenScraper(Integer.valueOf(args[0])).scrapeFrontPage();
			new ConsolePrinter().printToConsole(data);
		}
	}

	// TODO: Implementation
	private static boolean doInputChecks(String[] args) {
		boolean valid = false;
		if (!valid){
			System.err.println("Error in args");
		}
		return valid;
	}

}
