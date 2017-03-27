package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.XCalendarAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.XCalendarReactor;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarDefaultRector implements XCalendarReactor {
	//
	protected XCalendar calendar;
	
	/**
	 * 
	 */
	public XCalendarDefaultRector(XCalendar calendar) {
		this.calendar = calendar;
	}
	
	/**
	 * 
	 */
	@Override 
	public void submit(XCalendarAction action) {
		if(this.calendar.isDisposed()) return;
		this.calendar.getDisplay().syncExec(() -> { action.apply(this.calendar); });
	}
}
