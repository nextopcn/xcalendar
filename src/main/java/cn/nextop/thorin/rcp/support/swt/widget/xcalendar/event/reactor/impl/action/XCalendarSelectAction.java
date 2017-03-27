package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.bus.external.XCalendarSelectEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarSelectAction extends AbstractXCalendarAction {
	//
	private Date date;
	private boolean clear;
	
	public XCalendarSelectAction(@Nullable Date date) {
		this(date, false);
	}
	
	/**
	 * 
	 */
	public XCalendarSelectAction(@Nullable Date date, boolean clear) {
		super(Type.SELECT); this.date = date; this.clear = clear;
	}

	/**
	 * 
	 */
	@Override
	public void apply(final XCalendar calendar) {
		final XCalendarModel model = calendar.getModel();
		if (date != null) date = model.getCalendar().adjust(date, clear);
		model.setDate(date); publish(calendar, new XCalendarSelectEvent(date, model.getZoneId()), true);
	}
}