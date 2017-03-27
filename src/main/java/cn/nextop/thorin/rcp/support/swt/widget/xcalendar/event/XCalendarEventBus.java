package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.XCalendarEventHandler;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.listener.XCalendarEventListener;
import org.eclipse.swt.widgets.Event;

/**
 * 
 * @author Jingqi Xu
 */
public interface XCalendarEventBus {
	
	/**
	 * 
	 */
	void handle(Event event);
	
	boolean addHandler(XCalendarEventHandler reactor);
	
	boolean delHandler(XCalendarEventHandler reactor);
	
	/**
	 * 
	 */
	void publish(XCalendarEvent event);
	
	boolean addListener(XCalendarEventListener listener);
	
	boolean delListener(XCalendarEventListener listener);
}
