package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout.XCalendarLayout;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.XCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.List;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarDefaultLayout implements XCalendarLayout {
	//
	private Composite prevParent;
	private Rectangle prevBounds;
	
	/**
	 * 
	 */
	@Override
	public void layout(XCalendar popup) {
		layout(popup, this.prevParent, this.prevBounds);
	}
	
	/**
	 * 
	 */
	@Override
	public void layout(XCalendar popup, Composite parent, Rectangle bounds) {
		//
		this.prevParent = parent;
		this.prevBounds = bounds;
		final XCalendarModel model = popup.getModel();
		final XCalendarTheme theme = model.getTheme();
		int x = theme.getMargin(), y = theme.getMargin();
		int m = theme.getMargin(), w = theme.getWidth() - 2 * m;
		
		//
		List<XCalendarWidget> widgets = popup.getWidgets();
		for(int i = 0; i < widgets.size(); i++) {
			final XCalendarWidget v = widgets.get(i);
			final Pair<Point> points = v.locate(x, y, w, m);
			v.setBounds(x, y, points.getV1().x, points.getV1().y);
			x = points.getV2().x; y = points.getV2().y;
		}
		
		//
		final Shell shell = popup.getShell();
		shell.setSize(new Point(theme.getWidth(), y + m)); setLocation(popup);
	}

	/**
	 * 
	 */
	protected void setLocation(final XCalendar popup) {
		//
		final Shell shell = popup.getShell();
		final Display display = popup.getDisplay();
		final Rectangle r1 = display.map(prevParent, null, prevBounds);
		final Rectangle r2 = popup.getMonitor().getClientArea(); // Client area
		
		//
		final int margin = 2;
		final Point size = shell.getSize();
		int x = r1.x, y = r1.y + r1.height + margin;
		if(y + size.y > r2.y + r2.height) y = r1.y - size.y - margin;
		if(x < r2.x) x = r2.x; else if(x + size.x > r2.x + r2.width) x = r2.x + r2.width - size.x;
		shell.setLocation(x, y);
	}
}
