package databox.importer.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringUtil {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	protected StringUtil() {
		super();
	}

	public static String emptyIfNull(Object s) {
		if (s == null) {
			return "";
		}
		return "" + s;
	}

	public static String emptyIfNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

	public static String toString(final Object value, final String ifNull) {
		if (value == null) {
			return ifNull;
		}
		return toString(value);
	}

	/**
	 * @param value
	 * @return arrayToString for arrays, value.toString() or null if value is null
	 */
	public static String toString(final Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Object[]) {
			return arrayToString((Object[]) value, ",");
		}
		if (value instanceof int[]) {
			return arrayToString((int[]) value, ",");
		}
		if (value instanceof double[]) {
			return arrayToString((double[]) value, ",");
		}
		if (value instanceof float[]) {
			return arrayToString((float[]) value, ",");
		}
		return value.toString();
	}

	public static boolean isDigitsOnly(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public static boolean isLetterOrDigitOrUnderscore(final char ch) {
		return (ch == '_' || Character.isLetterOrDigit(ch));
	}

	public static boolean isLetterOrUnderscore(final char ch) {
		return (ch == '_' || Character.isLetter(ch));
	}

	public static Character firstChar(final String str) {
		if (str == null || str.length() < 1) {
			return null;
		}
		return Character.valueOf(str.charAt(0));
	}

	public static String toTitleCase(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}

		s = s.toLowerCase();

		boolean spaceBefore = true;

		final StringBuilder sb = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			final char curCh = s.charAt(i);

			if (spaceBefore) {
				sb.append(Character.toUpperCase(curCh));
			} else {
				sb.append(curCh);
			}

			if (isSpace(curCh)) {
				spaceBefore = true;
			} else {
				spaceBefore = false;
			}
		}
		return sb.toString();
	}

	public static boolean isSpace(final char ch) {
		switch (ch) {
			case ' ':
			case '\t':
			case '\n':
			case '\f':
			case '\r':
				return true;
			default:
				return false;
		}
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static int compare(String s1, String s2, boolean nullFirst) {
		if (s1 == null) {
			return s2 == null ? 0 : nullFirst ? -1 : 1;
		}
		if (s2 == null) {
			return nullFirst ? 1 : -1;
		}
		return s1.compareTo(s2);
	}

	public static int compare(String s1, String s2) {
		return compare(s1, s2, true);
	}

	public static int compareCaseInsensitive(String s1, String s2) {
		return compare(s1 != null ? s1.toUpperCase() : null, s2 != null ? s2.toUpperCase() : null);
	}

	public static boolean equalCaseInsensitive(String s1, String s2) {
		return compareCaseInsensitive(s1, s2) == 0;
	}

	public static String collectionToString(Iterable<?> collection, String separator) {
		if (collection == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Object obj : collection) {
			if (!first) {
				sb.append(separator);
			}
			first = false;
			sb.append(obj);
		}

		return sb.toString();

	}

	public static String arrayToString(int[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; i++) {
			sb.append(separator);
			sb.append(String.valueOf(arr[i]));
		}

		return sb.toString();
	}

	public static String arrayToString(double[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; i++) {
			sb.append(separator);
			sb.append(String.valueOf(arr[i]));
		}

		return sb.toString();
	}

	public static String arrayToString(float[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; i++) {
			sb.append(separator);
			sb.append(String.valueOf(arr[i]));
		}

		return sb.toString();
	}

	public static String arrayToString(Object[] arr, String separator) {
		return arrayToString(arr, separator, "", "");
	}

	public static String arrayToString(Object[] arr, String separator, String prolog, String epilog) {
		StringBuilder sb = new StringBuilder(prolog);
		int len = arr.length;
		if (len > 0) {
			sb.append(toString(arr[0]));
			for (int i = 1; i < arr.length; i++) {
				sb.append(separator);
				sb.append(toString(arr[i]));
			}
		}
		sb.append(epilog);
		return sb.toString();
	}

	public static Integer toInteger(String s) {
		return s == null ? null : Integer.valueOf(s);
	}

	public static int toInt(String s) {
		return Integer.parseInt(s);
	}

	public static int toInt(String s, int ifEmpty) {
		if (StringUtil.isNullOrEmpty(s)) {
			return ifEmpty;
		}
		return Integer.parseInt(s);
	}

	public static Integer tryToInteger(String s) {
		try {
			return toInteger(s);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}

	public static int tryToInt(String s) {
		return tryToInt(s, 0);
	}

	public static int tryToInt(String s, int ifFailed) {
		try {
			return toInt(s, ifFailed);
		} catch (NumberFormatException ignored) {
			return ifFailed;
		}
	}

	public static Long tryToLong(String s) {
		try {
			return toLong(s);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}

	public static long tryToLong(String s, long ifFailed) {
		try {
			Long value = toLong(s);
			if (value == null) {
				return ifFailed;
			}
			return value.longValue();
		} catch (NumberFormatException ignored) {
			return ifFailed;
		}
	}

	public static Float toFloatObj(String s) {
		return s == null ? null : Float.valueOf(s);
	}

	public static float toFloat(String s) {
		return Float.parseFloat(s);
	}

	public static float toFloat(String s, float ifEmpty) {
		if (StringUtil.isNullOrEmpty(s)) {
			return ifEmpty;
		}
		return Float.parseFloat(s);
	}

	public static Float tryToFloat(String s) {
		try {
			return toFloatObj(s);
		} catch (NumberFormatException ignored) {
			return null;
		}
	}

	public static float tryToFloat(String s, float ifFailed) {
		try {
			return toFloat(s, ifFailed);
		} catch (NumberFormatException ignored) {
			return ifFailed;
		}
	}

	public static Double toDoubleObj(String s) {
		return s == null ? null : Double.valueOf(s);
	}

	public static double toDouble(String s) {
		return Double.parseDouble(s);
	}

	public static double toDouble(String s, double ifEmpty) {
		if (StringUtil.isNullOrEmpty(s)) {
			return ifEmpty;
		}
		return Double.parseDouble(s);
	}

	public static Character toCharacter(String str) {
		return isNullOrEmpty(str) ? null : Character.valueOf(str.charAt(0));
	}

	public static Boolean toBoolean(String value) {
		return value == null ? null : Boolean.valueOf(value);
	}

	public static Long toLong(String value) {
		return value == null ? null : Long.valueOf(value);
	}

	public static String times(String string, int count) {
		StringBuilder sb = new StringBuilder(string);
		for (int i = 1; i < count; i++) {
			sb.append(string);
		}
		return sb.toString();
	}

	public static Set<String> splitToSet(String value, String separator) {
		return new HashSet<>(Arrays.asList(value.split(separator)));
	}

	public static ArrayList<String> splitToArrayList(String value, String separator) {
		String[] values = value.split(separator);
		return new ArrayList<>(Arrays.asList(values));
	}

	public static boolean isNumber(String str, boolean allowComma) {
		for (int i = 0; i < str.length(); i++) {
			boolean found = false;
			int c = str.charAt(i);
			if (allowComma && ((c == '.') || (c == ','))) {
				found = true;
			} else {
				for (char element : DIGITS) {
					if (c == element) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String str) {
		return isNumber(str, false);
	}

	public static String stripPrefix(String prefix, String s) {
		if (s.startsWith(prefix)) {
			return s.substring(prefix.length());
		}
		return s;
	}

	public static String stripSuffix(String s, String suffix) {
		if (s.endsWith(suffix)) {
			return s.substring(0, s.length() - suffix.length());
		}
		return s;
	}

	public static int count(String s, char c) {
		int cnt = 0;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				cnt++;
			}
		}

		return cnt;
	}

	public static int count(String s, String c) {
		int lastIndex = 0;
		int cnt = 0;

		while (lastIndex != -1) {
			lastIndex = s.indexOf(c, lastIndex);

			if (lastIndex != -1) {
				cnt++;
				lastIndex += c.length();
			}
		}

		return cnt;
	}

	public static String ifNullOrEmpty(String str, String ifNull) {
		return isNullOrEmpty(str) ? ifNull : str;
	}
}
