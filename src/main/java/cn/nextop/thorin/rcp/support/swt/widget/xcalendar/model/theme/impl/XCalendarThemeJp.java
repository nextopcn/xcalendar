package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarThemeJp extends XCalendarTheme {
	//
	private static final String[][] MONTH = { { "1月", "2月", "3月", "4月" }, 
			                                  { "5月", "6月", "7月", "8月" }, 
			                                  { "9月", "10月", "11月", "12月" } };
	
	private static final String[] MONTH_OF_YEAR_NAME = { "", "1月", "2月", "3月", 
			                                             "4月", "5月", "6月", "7月", 
			                                             "8月", "9月", "10月", "11月", "12月" };
	
	private static final String[] DAY_OF_WEEK_NAME = { "日", "月", "火", "水", "木", "金", "土" };
	
	/**
	 * 
	 */
	@Override public String[][] getMonthTheme(){ return MONTH; }
	@Override public String[] getDayOfWeekTheme() { return DAY_OF_WEEK_NAME; };
	@Override public String[] getMonthOfYearTheme() { return MONTH_OF_YEAR_NAME; }
	@Override public String header(int month, int year) { return year + "年" + getMonthOfYearTheme()[month] + "月"; }
}
