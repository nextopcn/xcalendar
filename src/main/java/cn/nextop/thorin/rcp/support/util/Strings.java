package cn.nextop.thorin.rcp.support.util;


/**
 * 
 * @author Jingqi Xu
 */
public final class Strings {
	//
	/**
	 * 
	 */
	public static int length(String s) {
		return s == null ? 0 : s.length();
	}
	
	public static String lower(final String s) {
		return s == null ? null : s.toLowerCase();
	}
	
	public static String upper(final String s) {
		return s == null ? null : s.toUpperCase();
	}
	
	public static String add(final String... s) {
		if(s == null) return "";
		final StringBuilder r = new StringBuilder();
		for(int i = 0; i < s.length; i++) r.append(s[i]);
		return r.toString();
	}
	
	/**
	 * 
	 */
	public static String trim(final String s) {
		return s == null ? null : s.trim();
	}
	
	public static String trimToNull(String s) {
		s = trim(s); return isEmpty(s) ? null : s;
	}
	
	public static String trimToEmpty(String s) {
		return s == null ? "" : s.trim();
	}
	
	public static String trim(String s, char c) {
		return rtrim(ltrim(s, c), c);
	}
	
	public static String trim(String s, int max) {
		if(s == null) return null; s = s.trim();
		if(s.length() > max) s = s.substring(0, max);
		return s;
	}
	
	public static String ltrim(String s, char trim) {
		if(s == null) return null; int i = 0;
		for(int length = s.length(); i < length; i++) {
			if(s.charAt(i) != trim) break;
		}
		return i >= s.length() ? "" : s.substring(i);
	}
	
	public static String rtrim(String s, char trim) {
		if(s == null) return null; int i = s.length() - 1;
		for(; i >= 0; i--) if (s.charAt(i) != trim) break;
		return i < 0 ? "" : s.substring(0, i + 1); // Empty
	}
	
	/**
	 * 
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}
	
	public static boolean isEmpty(String s, boolean trim) {
		if(s == null) return true;
		return trim ? s.trim().length() == 0 : s.length() == 0;
	}
	
	public static boolean isEquals(final String s1, final String s2) {
		return isEquals(s1, s2, false);
	}
	
	public static boolean isEquals(String s1, String s2, boolean ic) {
		if(s1 == s2) return true;
		else if(s1 == null && s2 == null) return true;
		else if(s1 == null || s2 == null) return false;
		else return ic ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
	}
	
	public static boolean isEquals(String s1, String s2, String... strs) {
		if(isEquals(s1, s2)) return true; else if(strs == null) return false;
		for(int i = 0; i < strs.length; i++) if(isEquals(s1, strs[i])) return true;
		return false;
	}
	
	/**
	 * 
	 */
	public static String toString(Object o) {
		return o == null ? "" : o.toString();
	}
	
	public static String[] toArray(String s) {
		return s == null ? null : new String[]{s};
	}
	
	public static String[] toArray(String... s) {
		return s;
	}
	
	public static String toString(Object[] array) {
		return toString(array, array.length);
	}
	
	public static String toString(byte[] array, int length) {
		final StringBuilder r = new StringBuilder("[");
		for(int i = 0, count = Math.min(length, array.length); i < count; i++) {
			r.append(array[i]); if(i < count - 1) r.append(",");
		}
		r.append("]"); return r.toString();
	}
	
	public static String toString(int[] array, int length) {
		final StringBuilder r = new StringBuilder("[");
		for(int i = 0, count = Math.min(length, array.length); i < count; i++) {
			r.append(array[i]); if(i < count - 1) r.append(",");
		}
		r.append("]"); return r.toString();
	}
	
	public static String toString(long[] array, int length) {
		final StringBuilder r = new StringBuilder("[");
		for(int i = 0, count = Math.min(length, array.length); i < count; i++) {
			r.append(array[i]); if(i < count - 1) r.append(",");
		}
		r.append("]"); return r.toString();
	}
	
	public static String toString(Object[] array, int length) {
		final StringBuilder r = new StringBuilder("[");
		for(int i = 0, count = Math.min(length, array.length); i < count; i++) {
			r.append(array[i]); if(i < count - 1) r.append(",");
		}
		r.append("]"); return r.toString();
	}
	
	/**
	 * 
	 */
	public static final String lappend(int src, int length, char padding) {
		return lappend(String.valueOf(src), length, padding);
	}
	
	public static final String rappend(int src, int length, char padding) {
		return rappend(String.valueOf(src), length, padding);
	}
	
	public static final String lappend(String src, int length, char padding) {
		//
		if(src != null && src.length() >= length) { return src; }
		
		//
		src = src == null ? "" : src;
		final StringBuilder r = new StringBuilder(length);
		for(int i = src.length(); i < length; i++) { r.append(padding); }
		r.append(src); return r.toString();
	}
	
	public static final String rappend(String src, int length, char padding) {
		//
		if(src != null && src.length() >= length) { return src; }
		
		//
		src = src == null ? "" : src;
		final StringBuilder r = new StringBuilder(length); r.append(src);
		for(int i = src.length() ; i < length; i++) { r.append(padding); } return r.toString();
	}
}
