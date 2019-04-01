package utils;

/**
 * Created by Vladimir Trandafilov on 01.04.2019.
 */
public enum Browser {
	CHROME,
	FIREFOX;

	private static Browser browser = null;

	public static Browser getCurrentBrowser() {
		if (browser == null) {
			browser = Browser.valueOf(System.getProperty("browser", "chrome").toUpperCase());
		}
		return browser;
	}
}
