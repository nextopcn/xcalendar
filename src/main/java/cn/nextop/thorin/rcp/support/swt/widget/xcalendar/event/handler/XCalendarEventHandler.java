package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler;

import org.eclipse.swt.widgets.Event;

/**
 * 
 * @author Jingqi Xu
 */
public interface XCalendarEventHandler {
	
	/**
	 * 
	 */
	int getPriority();
	
	boolean handle(Context context, Event event);
	
	/**
	 * 
	 */
	interface Context {
		
		XCalendarEventHandler getFocus();
		
		void setFocus(XCalendarEventHandler handler);
	}
}
