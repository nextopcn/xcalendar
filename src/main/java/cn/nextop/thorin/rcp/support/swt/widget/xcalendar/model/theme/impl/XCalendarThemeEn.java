package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarThemeEn extends XCalendarTheme {
	//
	private static final String[][] MONTH = { { "Jan", "Feb", "Mar", "Apr" }, 
			                                  { "May", "Jun", "Jul", "Aug" }, 
			                                  { "Sep", "Oct", "Nov", "Dec" } };
	
	private static final String[] MONTH_OF_YEAR_NAME = { "", "January", "February", "March",  
			                                             "April", "May", "June", "July", "August", 
			                                             "September", "October", "November", "December" };
	
	private static final String[] DAY_OF_WEEK_NAME = { "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa" };
	
	/**
	 * 
	 */
	@Override public String[][] getMonthTheme(){ return MONTH; }
	@Override public String[] getDayOfWeekTheme() { return DAY_OF_WEEK_NAME; };
	@Override public String[] getMonthOfYearTheme() { return MONTH_OF_YEAR_NAME; }
	@Override public String header(int month, int year) { return getMonthOfYearTheme()[month] + " "+ year; }
}
