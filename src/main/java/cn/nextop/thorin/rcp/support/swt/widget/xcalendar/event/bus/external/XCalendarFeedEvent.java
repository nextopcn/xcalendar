package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.external;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.AbstractXCalendarEvent;
import org.eclipse.swt.widgets.Event;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarFeedEvent extends AbstractXCalendarEvent {
	//
	private final Date value;
	
	/**
	 * 
	 */
	public Date getValue() {
		return value;
	}

	/**
	 * 
	 */
	public XCalendarFeedEvent(@Nullable Date value) {
		this(null, value);
	}
	
	public XCalendarFeedEvent(Event source, @Nullable Date value) {
		super(source); this.value = value;
	}
}