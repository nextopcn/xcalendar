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
public class XCalendarFeedAction extends AbstractXCalendarAction {
	//
	private Date date;
	
	/**
	 * 
	 */
	public XCalendarFeedAction(@Nullable Date date) {
		super(Type.FEED); this.date = date; 
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(final XCalendar calendar) {
		final XCalendarModel model = calendar.getModel();
		if(date != null) date = model.getCalendar().adjust(date);
		model.setDate(date); publish(calendar, new XCalendarSelectEvent(date, model.getZoneId()), true);
	}
}