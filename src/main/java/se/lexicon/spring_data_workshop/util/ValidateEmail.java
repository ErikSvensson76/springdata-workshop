package se.lexicon.spring_data_workshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Utility class that validate emails
 *
 */
public class ValidateEmail {
	
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static boolean validEmailPattern(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

}
