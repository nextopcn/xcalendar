package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;

import cn.nextop.thorin.rcp.support.swt.utility.graphics.Fonts;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static com.patrikdufresne.fontawesome.FontAwesome.calendar;
import static com.patrikdufresne.fontawesome.FontAwesome.clock_o;

/**
 * @author chenby
 *
 */
public class XCalendarSelectTimeWidget extends AbstractXCalendarWidget {
	
	/**
	 * 
	 */
	public XCalendarSelectTimeWidget(XCalendar popup) {
		super(popup);
	}
	
	/**
	 * 
	 */
	@Override
	public Pair<Point> locate(int x, int y, int w, int m) {
		XCalendarModel model = popup.getModel();
		boolean time = model.isTime(), nullable = model.isNullable();
		int u = w / 7; int c = 1 + (time ? 1 : 0) + (nullable ? 1 : 0);
		if (!nullable) {
			return new Pair<>(new Point(w / c, u), new Point(m, y + u));
		} else {
			return new Pair<>(new Point(w / c, u), new Point(x + w / c, y));
		}
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
		XCalendarModel m = popup.getModel(); m.getStateMachine().mutate(this);
		m.getConfig().getLayout().layout(popup); this.popup.redraw(); return true;
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
		final boolean hovered = this.mouse.isEntered();
		final boolean time = model.getStateMachine().isTime();
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(true, false, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());

		// Foreground
		String text = time ? calendar : clock_o;
		gc.setForeground(theme.getToolBarBackground(false));
		gc.setFont(Fonts.getAwesomeFont()); final Point size = extent(gc, text);
		gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
	}
}