package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarSelectAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.util.Strings;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.*;
import static cn.nextop.thorin.rcp.support.util.Objects.cast;

/**
 * @author chenby
 *
 */
public class XCalendarDayWidget extends AbstractXCalendarWidget {
	//
	private final int col, row;

	/**
	 * 
	 */
	public XCalendarDayWidget(XCalendar popup, int col, int row) {
		super(popup); this.col = col; this.row = row;
	}

	/**
	 * 
	 */
	@Override
	public Pair<Point> locate(int x, int y, int w, int m) {
		int unit = w / 7;
		if(col == 6) { x = m; y += unit; } else { x += unit; }
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
		if(!mouse.isEntered() || !enabled) { popup.redraw(); return true; }
		final ZoneId zoneId = popup.getModel().getZoneId();
		final XVirtualCalendar calendar = popup.getModel().getCalendar();
		final ZonedDateTime date = this.query(calendar, zoneId, col, row);
		super.submit(new XCalendarSelectAction(toDate(date))); return true;
	}
	
	/**
	 * 
	 */
	@Override
	protected <T> T query(XVirtualCalendar c, ZoneId zoneId, int col, int row) {
		final int year = c.getYear(), month = c.getMonth();
		final int h = c.getHour(), m = c.getMinute(), s = c.getSecond();
		final ZonedDateTime som = toDateTime(year, month, 1, h, m, s, 0, zoneId);
		return cast(som.plusDays((row * 7 + col) - (som.getDayOfWeek().getValue() % 7)));
	}
	
	/**
	 * 
	 */
	@Override
	public void render(XCalendarFrame frame) {
		// Background
		final GC gc = frame.getGc();
		final XCalendarModel model = popup.getModel();
		final XCalendarTheme theme = model.getTheme();
		final XVirtualCalendar calendar = model.getCalendar();
		final ZonedDateTime d1 = calendar.getCalendarDateTime();
		final ZonedDateTime d2 = query(calendar, model.getZoneId(), col, row);
		
		boolean hovered = this.mouse.isEntered();
		boolean same1 = isSameDate(d2, now(model.getZoneId()));
		this.enabled = isValidDate(toDate(d2), calendar::isValid);
		boolean selected = model.getDate() != null && isSameDate(d1, d2);
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(enabled, selected, same1, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());
		
		// Foreground
		String text = Strings.toString(d2.getDayOfMonth());
		boolean same2 = isSameYearMonth(d2, calendar.getTravelDateTime());
		gc.setForeground(theme.getForeground(this.enabled, selected, same2));
		gc.setFont(model.getTheme().getFont()); final Point size = extent(gc, text);
		gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
		
		if(!same1) return; int c = w / 7;
		gc.setClipping(x + c, y + c, w - 2 * c, h - 2 * c);
		gc.setBackground(theme.getForeground(true, selected, false));
		gc.fillPolygon(new int[] { x + w * 3 / 5, y + h, x + w, y + h, x + w, y + h * 3 / 5 });
		gc.setClipping((Rectangle) null);
	}
}
