package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.impl;

import cn.nextop.thorin.rcp.support.swt.utility.SwtUtils;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.internal.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.XCalendarWidget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarDefaultEventHandler extends AbstractXCalendarEventHandler {
	//
	protected enum Status { NORMAL, TRACKING; }
	
	//
	protected XCalendarWidget prev;
	protected Status status = Status.NORMAL;

	/**
	 * 
	 */
	public XCalendarDefaultEventHandler(XCalendar calendar) {
		super(calendar, 1);
	}
	
	/**
	 * 
	 */
	protected boolean reset(Context context, Event event) {
		//
		boolean r = false; this.status = Status.NORMAL; 
		if(context.getFocus() == this) context.setFocus(null);
		
		//
		final XCalendarWidget prev = this.prev; this.prev = null; 
		if(prev != null && !prev.isDisposed()) {
			r |= prev.handle(new XCalendarMouseLeaveEvent(event));
		}
		return r;
	}

	/**
	 * 
	 */
	@Override
	protected boolean accepts(Context context, Event event) {
		//
		final Widget widget = event.widget;
		if(calendar == null || calendar.isDisposed()) return false;
		
		//
		final Shell shell = calendar.getShell();
		if(widget != calendar && widget != shell) return false;
		return isMouseEvent(event) || event.type == SWT.Deactivate;
	}

	/**
	 * 
	 */
	@Override
	protected boolean process(Context context, Event event) {
		// Deactivated
		if (event.type == SWT.Deactivate) {
			reset(context, event);
			final Display display = calendar.getDisplay();
			SwtUtils.sync(display, () -> calendar.hide());
			return true;
		}
		
		//
		final int x = event.x, y = event.y;
		if (this.status == Status.NORMAL) {
			//
			if (this.prev != null && this.prev.isDisposed()) {
				this.prev = null;
			}
			
			// Exit
			final XCalendarWidget prev = this.prev;
			final XCalendarWidget next = calendar.getWidget(x, y);
			if (next == null || event.type == SWT.MouseExit) {
				return reset(context, event);
			}
			
			// Leave & Enter
			this.prev = next;
			if (prev != null && prev != next) {
				prev.handle(new XCalendarMouseLeaveEvent(event));
			}
			if (prev == null || prev != next) {
				next.handle(new XCalendarMouseEnterEvent(event));
			}
			
			// Up & Down & Move
			if (event.type == SWT.MouseMove) {
				return next.handle(new XCalendarMouseMoveEvent(event));
			} else if (event.type == SWT.MouseUp && event.button == 1) {
				return next.handle(new XCalendarMouseUpEvent(event));
			} else if (event.type == SWT.MouseDown && event.button == 1) {
				if(!next.handle(new XCalendarMouseDownEvent(event))) return false;
				context.setFocus(this); this.status = Status.TRACKING; return true;
			}
			return false;
		} else if(this.status == Status.TRACKING) {
			//
			final XCalendarWidget prev = this.prev;
			final XCalendarWidget next = calendar.getWidget(x, y);
			
			// Up
			if (event.type == SWT.MouseUp && event.button == 1) {
				//
				prev.handle(new XCalendarMouseUpEvent(event));
				this.status = Status.NORMAL; context.setFocus(null);
				
				//
				if (next == null || prev != next) {
					this.prev = null; prev.handle(new XCalendarMouseLeaveEvent(event));
				}
				if (next != null && prev != next) {
					this.prev = next; next.handle(new XCalendarMouseEnterEvent(event));
				}
			} else if (event.type == SWT.MouseMove || event.type == SWT.MouseExit) {
				//
				if (next == prev && !prev.getMouse().isEntered()) {
					prev.handle(new XCalendarMouseEnterEvent(event)); return true; // Enter
				} else if (next != prev && prev.getMouse().isEntered()) {
					prev.handle(new XCalendarMouseLeaveEvent(event)); return true; // Leave
				}
				
				//
				prev.handle(new XCalendarMouseMoveEvent(event)); // All events are routed to previous
			}
			return true; // Handle all events if current handler owns the focus & always returns true
		} else {
			throw new AssertionError("[XCalendarPopup]failed to handle event, invalid status: " + this.status);
		}
	}
}
