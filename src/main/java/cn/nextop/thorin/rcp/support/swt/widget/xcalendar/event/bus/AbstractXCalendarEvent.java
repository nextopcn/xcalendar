package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import org.eclipse.swt.widgets.Event;

/**
 * 
 * @author Jingqi Xu
 */
public abstract class AbstractXCalendarEvent implements XCalendarEvent {
	//
	protected Event source;
	protected boolean doit = true;
	
	/**
	 * 
	 */
	public AbstractXCalendarEvent() {
	}
	
	public AbstractXCalendarEvent(Event source) {
		this.source = source;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "source : " + source + ", doit : " + doit;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isDoit() {
		return doit;
	}

	@Override
	public void setDoit(boolean doit) {
		this.doit = doit;
	}
	
	@Override
	public Event getSource() {
		return source;
	}

	public final void setSource(Event source) {
		this.source = source;
	}
}
