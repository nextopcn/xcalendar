package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.XCalendarConfig;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarTime;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarTime.SECOND;

/**
 * @author chenby
 *
 */
public class XCalendarTimeWidget extends AbstractXCalendarWidget {
	//
	private final XCalendarTime type;

	/**
	 * 
	 */
	public XCalendarTimeWidget(XCalendar popup, XCalendarTime type) {
		super(popup); this.type = type; setMargin(new Point(10, 10));
	}
	
	/**
	 * 
	 */
	public XCalendarTime getType() {
		return type;
	}
	
	/**
	 * 
	 */
	@Override
	public Pair<Point> locate(int x, int y, int w, int m) {
		int unit = w / 3;
		if(type == SECOND) { x = m; y += unit; } else x += unit;
		return new Pair<>(new Point(unit, unit), new Point(x, y));
	}

	/**
	 * Event
	 */
	@Override
	protected boolean onMouseMove(XCalendarMouseMoveEvent event) {
		return true;
	}

	@Override
	protected boolean onMouseDown(XCalendarMouseDownEvent event) {
		return true;
	}

	@Override
	protected boolean onMouseEnter(XCalendarMouseEnterEvent event) {
		showCursor(); popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseLeave(XCalendarMouseLeaveEvent event) {
		hideCursor(); popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseUp(final XCalendarMouseUpEvent event) {
		if (!mouse.isEntered()) { popup.redraw(); return true; }
		final XCalendarModel model = popup.getModel();
		final XCalendarConfig config = model.getConfig();
		model.getStateMachine().mutate(this); // Mutate
		config.getLayout().layout(popup); popup.redraw(); return true;
	}

	/**
	 * 
	 */
	@Override
	public void render(XCalendarFrame frame) {
		// Background
		final GC gc = frame.getGc();
		final boolean hovered = mouse.isEntered();
		final XCalendarModel model = popup.getModel();
		final XCalendarTheme theme = model.getTheme();
		final XVirtualCalendar calendar = model.getCalendar();
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(true, false, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());

		// Foreground
		String t1 = this.type.text(theme, calendar);
		gc.setForeground(theme.getForeground(true, false, true));
		gc.setFont(theme.getBold()); final Point s1 = extent(gc, t1);
		gc.drawText(t1, x + 1 + ((w - s1.x) >> 1), y + 1 + ((h - s1.y) >> 1));
		
		if(this.type == XCalendarTime.HOUR || this.type == XCalendarTime.MINUTE) {
			String t2 = ":"; Point s2 = extent(gc, t2);
			gc.setBackground(theme.getBackground(true, false, false, false));
			gc.drawText(t2, x + 1 + w + (getMargin().x >> 2), y + 1 + ((h - s2.y) >> 1));
		}
	}
}
