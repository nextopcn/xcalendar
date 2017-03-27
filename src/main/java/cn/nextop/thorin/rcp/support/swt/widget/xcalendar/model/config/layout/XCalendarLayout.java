package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Jingqi Xu
 */
public interface XCalendarLayout {
	
	/**
	 * 
	 */
	void layout(XCalendar calendar);
	
	void layout(XCalendar calendar, Composite parent, Rectangle bounds);
}
