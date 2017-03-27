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
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.*;
import static cn.nextop.thorin.rcp.support.util.Objects.cast;

/**
 * @author chenby
 *
 */
public class XCalendarMonthWidget extends AbstractXCalendarWidget {
	//
	private final int col, row;
	
	/**
	 * 
	 */
	public XCalendarMonthWidget(XCalendar popup, int col, int row) {
		super(popup); this.col = col;this.row = row;
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
		if(this.enabled) showCursor();  popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseLeave(XCalendarMouseLeaveEvent event) {
		if(this.enabled) hideCursor(); popup.redraw(); return true;
	}

	@Override
	protected boolean onMouseUp(final XCalendarMouseUpEvent event) {
		if(!mouse.isEntered() || !enabled) { popup.redraw(); return true; }
		final XCalendarModel model = popup.getModel();
		final XCalendarConfig config = model.getConfig();
		final XVirtualCalendar calendar = model.getCalendar();
		final int m = query(calendar, model.getZoneId(), col, row);
		if (!model.isYearMonth()) {
			calendar.setMonth(m); model.getStateMachine().mutate(this);
			config.getLayout().layout(popup); popup.redraw(); return true;
		} else {
			final int y = calendar.getYear();
			return submit(new XCalendarSelectAction(som(y, m, model.getZoneId())));
		}
	}
	
	/**
	 * 
	 */
	@Override
	protected <T> T query(XVirtualCalendar calendar, ZoneId z, int col, int row) {
		int idx = row * 4 + col; return cast(idx + 1); 
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
		
		ZoneId zoneId = model.getZoneId();
		final int year = calendar.getYear();
		final int month = query(calendar, zoneId, col, row);
		final ZonedDateTime d2 = calendar.getTravelDateTime();
		final ZonedDateTime d1 = calendar.getCalendarDateTime();
		boolean selected = isSameYear(d1, d2) && month == d1.getMonthValue();
		this.enabled = isValidYearMonth(year, month, zoneId, calendar::isValid);
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(enabled, selected, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());
		
		// Foreground
		String text = theme.getMonthTheme()[row][col];
		gc.setForeground(theme.getForeground(enabled, selected, true));
		gc.setFont(theme.getFont()); final Point size = extent(gc, text);
		gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
	}
}
