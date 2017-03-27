package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.external;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.AbstractXCalendarEvent;
import org.eclipse.swt.widgets.Event;

import javax.annotation.Nullable;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarSelectEvent extends AbstractXCalendarEvent {
	//
	private final Date value;
	private final ZoneId zoneId;

	/**
	 * 
	 */
	public XCalendarSelectEvent(@Nullable Date value, ZoneId zoneId) {
		this(null, value, zoneId);
	}
	
	public XCalendarSelectEvent(Event source, @Nullable Date value, ZoneId zoneId) {
		super(source); this.value = value; this.zoneId = zoneId;
	}
	
	/**
	 * 
	 */
	public Date getValue() {
		return value;
	}
	
	public Integer getYear() {
		return value == null ? null : YearMonth.from(value.toInstant().atZone(zoneId)).getYear();
	}
	
	public YearMonth getYearMonth() {
		return value == null ? null : YearMonth.from(value.toInstant().atZone(zoneId));
	}
}