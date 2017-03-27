package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.tracker.XCalendarMouseTracker;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * @author Baoyi Chen
 */
public interface XCalendarWidget {

	/**
	 * 
	 */
	void dispose();

	boolean isDisposed();

	XCalendarMouseTracker getMouse();
	
	void render(XCalendarFrame frame);

	boolean handle(XCalendarEvent event);
	
	/**
	 * 
	 */
	Point getMargin();
	
	Rectangle getBounds();
	
	void setMargin(Point margin);
	
	void setBounds(int x, int y, int w, int h);
	
	Pair<Point> locate(int x, int y, int maxWidth, int margin);
}
