package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;

/**
 * 
 * @author Jingqi Xu
 */
public interface XCalendarAction {
	
	/**
	 * 
	 */
	Type getType();
	
	void apply(XCalendar calendar);
	
	/**
	 * 
	 */
	enum Type { FEED, SETUP, SELECT, RESIZE; }
}
