package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.XCalendarEvent;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.XCalendarAction;
import org.eclipse.swt.widgets.Event;

/**
 *
 * @author Jingqi Xu
 */
public abstract class AbstractXCalendarAction implements XCalendarAction {
	//
	protected final Type type;
	protected final Event event;

	/**
	 *
	 */
	public AbstractXCalendarAction(Type type) {
		this.type = type; this.event = null;
	}

	public AbstractXCalendarAction(Type type, Event event) {
		this.type = type; this.event = event;
	}

	/**
	 *
	 */
	@Override
	public final Type getType() {
		return this.type;
	}

	/**
	 *
	 */
	public final Event getEvent() {
		return this.event;
	}

	/**
	 *
	 */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("type:" + type)
                .append("event:" + event).toString();
    }

    /**
	 *
	 */
	protected boolean confirm(XCalendar calendar, XCalendarEvent event) {
		calendar.getBus().publish(event); return event.isDoit();
	}

	protected boolean publish(XCalendar calendar, XCalendarEvent event, boolean redraw) {
		calendar.getBus().publish(event);
		if(redraw && !calendar.isDisposed()) { calendar.redraw(); } return event.isDoit();
	}
}
