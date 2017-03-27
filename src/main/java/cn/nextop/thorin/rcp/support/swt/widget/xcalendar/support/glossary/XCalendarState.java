package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.util.Strings;
import cn.nextop.thorin.rcp.support.util.type.Range;
import org.eclipse.swt.SWTException;

/**
 * 
 * @author Baoyi Chen
 */
public enum XCalendarState {
	//
	DATE, MONTH, YEAR, DECADE, TIME, HOUR, MINUTE, SECOND;
	
	/**
	 * 
	 */
	public void prev(XVirtualCalendar calendar) {
		switch(this){
		case DATE: calendar.prevMonth(); break;
		case MONTH: calendar.prevYear(); break;
		case YEAR: calendar.prevDecade(); break;
		case DECADE: calendar.prevCentury(); break;
		default: throw new SWTException("invalid calendar state: " + this);
		}
	}
	
	public void next(XVirtualCalendar calendar) {
		switch(this) {
		case DATE: calendar.nextMonth(); break;
		case MONTH: calendar.nextYear(); break;
		case YEAR: calendar.nextDecade(); break;
		case DECADE: calendar.nextCentury(); break;
		default: throw new SWTException("invalid calendar state: " + this);
		}
	}

	/**
	 * 
	 */
	public String text(XCalendarTheme theme, XVirtualCalendar calendar) {
		switch (this) {
		case MONTH:
			return Strings.toString(calendar.getYear());
		case YEAR:
			Range<Integer> decade = calendar.getDecade();
			return decade.getValue1() + "-" + decade.getValue2();
		case DECADE:
			Range<Integer> century = calendar.getCentury();
			return century.getValue1() + "-" + century.getValue2();
		case DATE:
			return theme.header(calendar.getMonth(), calendar.getYear());
		default: throw new SWTException("invalid calendar state: " + this);
		}
	}
}
