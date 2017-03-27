package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary;


import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import org.eclipse.swt.SWTException;

import static cn.nextop.thorin.rcp.support.util.Strings.lappend;

/**
 * 
 * @author Baoyi Chen
 */
public enum XCalendarTime {
	//
	HOUR, MINUTE, SECOND;
	
	/**
	 * 
	 */
	public void decr(XVirtualCalendar calendar) {
		switch(this) {
		case HOUR: calendar.prevHour(); break;
		case MINUTE: calendar.prevMinute(); break;
		case SECOND: calendar.prevSecond(); break;
		default: throw new SWTException("invalid calendar time: " + this);
		}
	}
	
	public void incr(XVirtualCalendar calendar) {
		switch(this) {
		case HOUR: calendar.nextHour(); break;
		case MINUTE: calendar.nextMinute(); break;
		case SECOND: calendar.nextSecond(); break;
		default: throw new SWTException("invalid calendar time: " + this);
		}
	}

	/**
	 * 
	 */
	public String text(XCalendarTheme theme, XVirtualCalendar calendar) {
		switch(this) {
		case HOUR: return lappend(calendar.getHour(), 2, '0');
		case MINUTE: return lappend(calendar.getMinute(), 2, '0');
		case SECOND: return lappend(calendar.getSecond(), 2, '0');
		default: throw new SWTException("invalid calendar time: " + this);
		}
	}
}
