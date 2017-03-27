package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.XCalendarEventHandler;
import org.eclipse.swt.widgets.Event;

import static org.eclipse.swt.SWT.*;

/**
 * 
 * @author Jingqi Xu
 */
public abstract class AbstractXCalendarEventHandler implements XCalendarEventHandler {
	//
	protected final int priority;
	protected final XCalendar calendar;
	
	//
	protected abstract boolean accepts(Context c, Event e);
	protected abstract boolean process(Context c, Event e);
	
	/**
	 * 
	 */
	public AbstractXCalendarEventHandler(XCalendar x, int p) {
		this.calendar = x; this.priority = p;
	}
	
	/**
	 * 
	 */
	@Override 
	public int getPriority() {
		return this.priority; 
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("priority:" + priority)
				.append("calendar:" + calendar).toString();
	}
	
	/**
	 * 
	 */
	protected boolean isMouseEvent(Event event) {
		final int t = event.type;
		if(t == MouseEnter || t == MouseExit) return true;
		return t == MouseUp || t == MouseMove || t == MouseDown;
	}
	
	/**
	 * 
	 */
	@Override
	public final boolean handle(Context ctx, Event event) {
		final XCalendarEventHandler focus = ctx.getFocus();
		if (focus != null && focus != this) return false;
		return !accepts(ctx, event) ? false : process(ctx, event);
	}
}
