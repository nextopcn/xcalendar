package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl;


import cn.nextop.thorin.rcp.support.swt.utility.graphics.Fonts;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action.XCalendarSelectAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.AbstractXCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.util.type.Pair;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

import static cn.nextop.thorin.rcp.support.swt.utility.graphics.Extents.extent;
import static com.patrikdufresne.fontawesome.FontAwesome.trash_o;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarClearWidget extends AbstractXCalendarWidget {
	
	/**
	 * 
	 */
	public XCalendarClearWidget(XCalendar popup) {
		super(popup);
	}
	
	/**
	 * 
	 */
	@Override
	public Pair<Point> locate(int x, int y, int w, int m) {
		XCalendarModel model = popup.getModel();
		boolean time = model.isTime(), nullable = model.isNullable();
		int unit = w / 7, c = 1 + (time ? 1 : 0) + (nullable ? 1 : 0);
		return new Pair<>(new Point(w / c, unit), new Point(m, y + unit));
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
		super.submit(new XCalendarSelectAction(null)); return true;
	}
	
	/**
	 * Render
	 */
	@Override
	public void render(XCalendarFrame frame) {
		// Background
		final GC gc = frame.getGc();
		final XCalendarModel model = popup.getModel();
		final XCalendarTheme theme = model.getTheme();
		final boolean hovered = this.mouse.isEntered();
		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;
		gc.setBackground(theme.getBackground(true, false, false, hovered));
		gc.fillRoundRectangle(x, y, w, h, theme.getArc(), theme.getArc());

		// Foreground
		String text = trash_o;
		gc.setForeground(theme.getToolBarBackground(false));
		gc.setFont(Fonts.getAwesomeFont()); final Point size = extent(gc, text);
		gc.drawText(text, x + 1 + ((w - size.x) >> 1), y + 1 + ((h - size.y) >> 1));
	}
}