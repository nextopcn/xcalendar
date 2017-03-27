package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.impl.XCalendarThemeEn;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.impl.XCalendarThemeJp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarThemeFactory {
	//
	private Map<Locale, XCalendarTheme> map = new HashMap<>();
	
	/**
	 * 
	 */
	public XCalendarThemeFactory() {
		register(Locale.ENGLISH, new XCalendarThemeEn());
		register(Locale.JAPANESE, new XCalendarThemeJp());
	}
	
	/**
	 * 
	 */
	public XCalendarTheme create(Locale locale) {
		return map.getOrDefault(locale, new XCalendarThemeEn());
	}
	
	/**
	 * 
	 */
	protected void register(Locale locale, XCalendarTheme theme) {
		map.put(locale, theme);
	}
}
