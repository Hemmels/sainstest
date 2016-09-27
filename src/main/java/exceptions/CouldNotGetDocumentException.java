package exceptions;

public class CouldNotGetDocumentException extends Exception {

	private static final long serialVersionUID = 1L;

	public CouldNotGetDocumentException(String page) {
		super(page);
	}

}
