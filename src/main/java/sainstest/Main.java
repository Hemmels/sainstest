package sainstest;

public class Main {
	
	private String errorMessage;

	public static void main(String[] args) {
		boolean validInput = doInputChecks();
		
		if (validInput){
			Object data = new ScreenScraper(Integer.valueOf(args[0])).scrapeFrontPage();
			new ConsolePrinter().printToConsole(data);
		}
		else{
			printError();
		}
	}

	// TODO: Implementation
	private static boolean doInputChecks() {
		return false;
	}

	private static void printError() {
		// TODO: Input validation
		System.err.println("Invalid input.");
	}

}
