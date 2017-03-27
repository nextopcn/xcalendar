package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;


import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarSelectAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.XCalendarConfig;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.util.Strings;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import java.time.ZoneId;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.*;
import static cn.nextop.thorin.rcp.support.util.Objects.cast;

/**
 * @author chenby
 *
 */
public class XCalendarYearWidget extends AbstractXCalendarWidget {
	//
	public final int col, row;
	
	/**
	 * 
	 */
	public XCalendarYearWidget(XCalendar popup, int col, int row) {
		super(popup); this.col = col; this.row = row;
	}
	
	/**
	 * 
	 */
	@Override
	public Pair<Point> locate(int x, int y, int w, int m) {
		int unit = w / 4;
		if(col == 3) { x = m; y += unit; } else { x += unit; }
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
		if(this.enabled) showCursor(); popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseLeave(XCalendarMouseLeaveEvent event) {
		if(this.enabled) hideCursor(); popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseUp(final XCalendarMouseUpEvent event) {
		if (!mouse.isEntered() || !enabled) { popup.redraw(); return true; }
		final XCalendarModel model = popup.getModel();
		final XCalendarConfig config = model.getConfig();
		final XVirtualCalendar calendar = model.getCalendar();
		final int y = query(calendar, model.getZoneId(), col, row);
		if (!model.isYear()) {
			calendar.setYear(y); model.getStateMachine().mutate(this);
			config.getLayout().layout(popup); popup.redraw(); return true;
		} else {
			return submit(new XCalendarSelectAction(soy(y, model.getZoneId())));
		}
	}
	
	/**
	 * 
	 */
	@Override
	protected <T> T query(XVirtualCalendar c, ZoneId z, int col, int row) {
		int idx = row * 4 + col; return cast(c.getDecade().getValue1() + idx); 
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
		final int year = query(calendar, model.getZoneId(), col, row);
		this.enabled = isValidYear(year, model.getZoneId(), calendar::isValid);
		boolean same = isSameYear(year, calendar.getCalendarDateTime().getYear());
		final int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(enabled, same, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());
		
		// Foreground
		String text = Strings.toString(year);
		gc.setForeground(theme.getForeground(enabled, same, true));
		gc.setFont(theme.getFont()); final Point size = extent(gc, text);
		gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
	}
}
