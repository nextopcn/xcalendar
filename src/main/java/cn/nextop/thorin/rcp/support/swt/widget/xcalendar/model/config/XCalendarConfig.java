package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout.XCalendarLayout;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.layout.impl.XCalendarDefaultLayout;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarRender;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.impl.XCalendarDefaultRender;

/**
 * 
 * @author Jingqi Xu
 */
public class XCalendarConfig {
	//
	protected XCalendarLayout layout;
	protected XCalendarRender render;
	
	/**
	 * 
	 */
	public XCalendarConfig() {
		this.layout = new XCalendarDefaultLayout();
		this.render = new XCalendarDefaultRender();
	}

	/**
	 * 
	 */
	public final XCalendarLayout getLayout() {
		return layout;
	}

	public final void setLayout(XCalendarLayout layout) {
		this.layout = layout;
	}

	public final XCalendarRender getRender() {
		return render;
	}

	public final void setRender(XCalendarRender render) {
		this.render = render;
	}
}
