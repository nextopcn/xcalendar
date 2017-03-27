package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event;

import org.eclipse.swt.widgets.Event;

/**
 * 
 * @author Jingqi Xu
 */
public interface XCalendarEvent {
	
	Event getSource();
	
	boolean isDoit(); void setDoit(boolean doit);
}
