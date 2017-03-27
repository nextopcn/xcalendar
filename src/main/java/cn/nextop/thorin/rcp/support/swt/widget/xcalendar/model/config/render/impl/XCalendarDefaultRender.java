package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.impl;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarFrame;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.render.XCalendarRender;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.XCalendarWidget;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;

import java.util.List;

/**
 * @author chenby
 *
 */
public class XCalendarDefaultRender implements XCalendarRender {
	
	public void render(XCalendar popup, GC gc) {
		final XCalendarModel model = popup.getModel();
		final XCalendarTheme theme = model.getTheme();
		final Transform t = new Transform(gc.getDevice());
		
		//
		try (final XCalendarFrame frame = new XCalendarFrame(gc, t)) {
			//
			Rectangle area = new Rectangle((area = popup.getClientArea()).x, area.y, area.width - 1, area.height - 1);
			gc.setAdvanced(true);
			gc.setAntialias(SWT.ON);
			gc.setBackground(theme.getBackground(true, false, false, false));
			gc.fillRectangle(area); gc.setForeground(theme.getGrid(true)); gc.drawRectangle(area);
			
			List<XCalendarWidget> widgets = popup.getWidgets();
			for(XCalendarWidget widget:widgets) widget.render(frame);
		}
	}
}