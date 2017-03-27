package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget;


import cn.nextop.thorin.rcp.support.swt.utility.graphics.Cursors;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.XCalendarAction;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.tracker.XCalendarMouseTracker;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import java.time.ZoneId;

import static cn.nextop.thorin.rcp.support.util.Objects.cast;

/**
 * 
 * @author Baoyi Chen
 */
public abstract class AbstractXCalendarWidget implements XCalendarWidget {
	//
	protected Point margin;
	protected Rectangle bounds;
	protected boolean disposed;
	protected final XCalendar popup;
	protected boolean enabled = true;
	protected XCalendarMouseTracker mouse;
	
	/**
	 * 
	 */
	public AbstractXCalendarWidget(XCalendar popup) {
		this.popup = popup;
		this.disposed = false;
		this.margin = new Point(1, 1);
		this.bounds = new Rectangle(0, 0, 0, 0);
		this.mouse = new XCalendarMouseTracker();
	}
	
	/**
	 * 
	 */
	@Override
	public void dispose() {
		this.disposed = true;
	}

	@Override
	public boolean isDisposed() {
		return this.disposed;
	}

	@Override
	public final Point getMargin() {
		return this.margin;
	}
	
	@Override
	public final Rectangle getBounds() {
		return this.bounds;
	}
	
	@Override
	public void setMargin(Point margin) {
		this.margin = margin;
	}

	@Override
	public XCalendarMouseTracker getMouse() {
		return this.mouse;
	}
	@Override
	public void setBounds(int x, int y, int w, int h) {
		int mx = margin.x, my = margin.y;
		bounds = new Rectangle(x + mx, y + my, w - (mx << 1), h - (my << 1));
	}
	
	/**
	 * 
	 */
	protected void showCursor() {
		this.popup.setCursor(Cursors.CURSOR_HAND);
	}
	
	protected void hideCursor() {
		this.popup.setCursor(Cursors.CURSOR_ARROW);
	}
	
	protected boolean submit(final XCalendarAction action) {
		this.popup.getReactor().submit(action); return true;
	}
	
	protected <T> T query(XVirtualCalendar v, ZoneId z, int col, int row) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Event
	 */
	protected boolean onMouseUp(XCalendarMouseUpEvent event) {
		return false;
	}
	
	protected boolean onMouseDown(XCalendarMouseDownEvent event) {
		return false;
	}
	
	protected boolean onMouseMove(XCalendarMouseMoveEvent event) {
		return false;
	}
	
	protected boolean onMouseEnter(XCalendarMouseEnterEvent event) {
		return false;
	}
	
	protected boolean onMouseLeave(XCalendarMouseLeaveEvent event) {
		return false;
	}
	
	@Override
	public boolean handle(final XCalendarEvent event) {
		//
		if(!popup.getEnabled() || !popup.getEditable()) return false; 
		
		//
		this.mouse.track(event);
		if(event instanceof XCalendarMouseUpEvent) return onMouseUp(cast(event));
		else if(event instanceof XCalendarMouseDownEvent) return onMouseDown(cast(event));
		else if(event instanceof XCalendarMouseMoveEvent) return onMouseMove(cast(event));
		else if(event instanceof XCalendarMouseEnterEvent) return onMouseEnter(cast(event));
		else if(event instanceof XCalendarMouseLeaveEvent) return onMouseLeave(cast(event));
		return false;
	}
}
