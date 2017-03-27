package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEventBus;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.XCalendarEventHandler;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.listener.XCalendarEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author Jingqi Xu
 */
public abstract class AbstractXCalendarEventBus implements XCalendarEventBus {
	//
	protected final XCalendar calendar;
	protected final List<XCalendarEventHandler> handlers;
	protected final List<XCalendarEventListener> listeners;
	
	/**
	 * 
	 */
	public AbstractXCalendarEventBus(XCalendar calendar) {
		this.calendar = calendar;
		this.handlers = new CopyOnWriteArrayList<>();
		this.listeners = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean addListener(XCalendarEventListener listener) {
		return this.listeners.add(listener);
	}
	
	@Override
	public boolean delListener(XCalendarEventListener listener) {
		return this.listeners.remove(listener);
	}
	
	@Override
	public boolean delHandler(final XCalendarEventHandler handler) {
		return this.handlers.remove(handler);
	}
	
	@Override
	public boolean addHandler(final XCalendarEventHandler handler) {
		int i = Collections.binarySearch(this.handlers, handler, ASC);
		this.handlers.add(i < 0 ? -(i + 1) : i + 1, handler); return true;
	}
	
	/**
	 * 
	 */
	protected static final Comparator<XCalendarEventHandler> ASC = new Comparator<XCalendarEventHandler>() {
		@Override public int compare(XCalendarEventHandler o1, XCalendarEventHandler o2) { return o1.getPriority() - o2.getPriority(); }
	};
}
