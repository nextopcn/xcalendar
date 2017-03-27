package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;

import java.util.Date;
import java.util.function.Predicate;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarSetupAction extends AbstractXCalendarAction {
	//
	private final boolean nullable;
	private final Predicate<Date> predicate;
	
	/**
	 * 
	 */
	public XCalendarSetupAction(Predicate<Date> v1, boolean v2) {
		super(Type.SETUP); this.predicate = v1; this.nullable = v2; 
	}

	/**
	 * 
	 */
	@Override
	public void apply(XCalendar calendar) {
		calendar.getModel().setNullable(this.nullable);
		calendar.getModel().getCalendar().setFilter(this.predicate);
	}
}
