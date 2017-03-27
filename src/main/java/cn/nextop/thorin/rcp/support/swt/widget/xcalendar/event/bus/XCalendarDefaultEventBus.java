package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.handler.XCalendarEventHandler;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.listener.XCalendarEventListener;
import org.eclipse.swt.widgets.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarDefaultEventBus extends AbstractXCalendarEventBus implements XCalendarEventHandler.Context {
	//
	private static Logger LOGGER = LoggerFactory.getLogger(XCalendarDefaultEventBus.class);
	
	//
	protected XCalendarEventHandler focus;
	
	/**
	 * 
	 */
	public XCalendarDefaultEventBus(XCalendar datetime) {
		super(datetime);
	}
	
	/**
	 * 
	 */
	@Override
	public XCalendarEventHandler getFocus() {
		return focus;
	}

	@Override
	public void setFocus(XCalendarEventHandler focus) {
		this.focus = focus;
	}
	
	/**
	 * 
	 */
	@Override
	public void handle(final Event event) {
		for(final XCalendarEventHandler handler : this.handlers) {
			try {
				if(handler.handle(this, event)) break;
			} catch(Throwable tx) {
				LOGGER.error("[XCalendar.event.bus]failed to handle event: " + event, tx);
			}
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void publish(final XCalendarEvent event) {
		for(final XCalendarEventListener listener : this.listeners) {
			try {
				listener.onEvent(event);
			} catch(Throwable tx) {
				LOGGER.error("[XCalendar.event.bus]failed to publish event: " + event, tx);
			}
		}
	}
}
