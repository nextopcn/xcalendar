package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.event.reactor.impl.action;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.XCalendarConfig;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarResizeAction extends AbstractXCalendarAction {
	//
	private final Composite parent;
	private final Rectangle bounds;
	
	/**
	 * 
	 */
	public XCalendarResizeAction(Composite parent, Rectangle bounds) {
		super(Type.RESIZE);
		this.parent = parent; this.bounds = bounds;
	}
	
	/**
	 * 
	 */
	@Override
	public void apply(final XCalendar calendar) {
		final XCalendarModel model = calendar.getModel();
		final XCalendarConfig config = model.getConfig();
		config.getLayout().layout(calendar, parent, bounds); calendar.redraw();
	}
}
