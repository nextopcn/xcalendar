package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;


import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.util.Strings;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import cn.nextop.thorin.rcp.support.util.type.Range;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import java.time.ZoneId;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.DECADE_OFFSET;
import static cn.nextop.thorin.rcp.support.util.Objects.cast;
import static cn.nextop.thorin.rcp.support.util.type.Range.closed;

/**
 * @author chenby
 *
 */
public class XCalendarDecadeWidget extends AbstractXCalendarWidget {
	//
	private final int col,row;
	
	/**
	 * 
	 */
	public XCalendarDecadeWidget(XCalendar popup, int col, int row) {
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
		final XVirtualCalendar calendar = model.getCalendar();
		Range<Integer> decade = query(calendar, model.getZoneId(), col, row);
		if(decade != null) { // Last 3 decades are always null
			model.getStateMachine().mutate(this);
			model.getCalendar().setDecade(decade.getValue1());
		}
		model.getConfig().getLayout().layout(popup); popup.redraw(); return true;
	}
	
	/**
	 * 
	 */
	@Override
	protected <T> T query(XVirtualCalendar c, ZoneId z, int col, int row) {
		int idx = row * 4 + col; int from; 
		final Integer v = DECADE_OFFSET[idx]; if(v == null) return null;
		return cast(closed((from = c.getCentury().getValue1() + v), from + 11));
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
		Range<Integer> decade = query(calendar, model.getZoneId(), col, row);
		int year = model.getCalendar().getCalendarDateTime().getYear();
		final boolean selected = decade != null && decade.contains(year);
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(true, selected, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());
		
		// Foreground
		String text = "";
		if (decade != null) {
			Integer v1 = decade.getValue1(), v2 = decade.getValue2();
			text = Strings.toString(v1) + "-" + Strings.toString(v2);
		}
		
		gc.setForeground(theme.getForeground(true, false, true));
		gc.setFont(theme.getFont()); Point size = extent(gc, text);
		if(size.x > w) {
			String text1 = text.substring(0, text.length() / 2);
			final String text2 = text.substring(text.length() / 2);
			Point size1 = extent(gc, text1); Point size2 = extent(gc, text2);
			gc.drawText(text1, x + 1 + ((w - size1.x) >> 1), y + 5);
			gc.drawText(text2, x + 1 + ((w - size2.x) >> 1), y + 10 + size1.y);
		} else {
			gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
		}
	}
}
