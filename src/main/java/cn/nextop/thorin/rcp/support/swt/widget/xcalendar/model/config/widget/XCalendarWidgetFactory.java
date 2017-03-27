package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.XCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.XCalendarModel;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarTime;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarWidgetFactory {
	
	/**
	 * 
	 */
	public static Map<XCalendarState, List<XCalendarWidget>> create(XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> headers = new LinkedList<>();
		headers.add(new XCalendarPrevWidget(popup));
		headers.add(new XCalendarSelectWidget(popup));
		headers.add(new XCalendarNextWidget(popup));
		
		Map<XCalendarState, List<XCalendarWidget>> r = new HashMap<>();
		decade(r, headers, popup);
		year(r, headers, popup); if(model.isYear()) return r;
		month(r, headers, popup); if(model.isYearMonth()) return r;
		day(r, headers, popup); if (model.isDate()) return r;
		time(r, popup); hour(r, popup); minute(r, popup); second(r, popup); return r;
	}

	/**
	 * 
	 */
	private static void time(Map<XCalendarState, List<XCalendarWidget>> out, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		widgets.add(new XCalendarSelectTodayWidget(popup));
		widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		
		widgets.add(new XCalendarIncrWidget(popup, XCalendarTime.HOUR));
		widgets.add(new XCalendarIncrWidget(popup, XCalendarTime.MINUTE));
		widgets.add(new XCalendarIncrWidget(popup, XCalendarTime.SECOND));
		
		widgets.add(new XCalendarTimeWidget(popup, XCalendarTime.HOUR));
		widgets.add(new XCalendarTimeWidget(popup, XCalendarTime.MINUTE));
		widgets.add(new XCalendarTimeWidget(popup, XCalendarTime.SECOND));
		
		widgets.add(new XCalendarDecrWidget(popup, XCalendarTime.HOUR));
		widgets.add(new XCalendarDecrWidget(popup, XCalendarTime.MINUTE));
		widgets.add(new XCalendarDecrWidget(popup, XCalendarTime.SECOND));
		out.put(XCalendarState.TIME, widgets);
	}
	
	private static void hour(Map<XCalendarState, List<XCalendarWidget>> out, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		
		int hourColumn = 4, hourRow = 6;
		widgets.add(new XCalendarSelectTodayWidget(popup));
		widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		
		for (int row = 0; row < hourRow; row++)
			for (int col = 0; col < hourColumn; col++)
				widgets.add(new XCalendarHourWidget(popup, col, row));

		out.put(XCalendarState.HOUR, widgets);
	}
	
	private static void minute(Map<XCalendarState, List<XCalendarWidget>> out, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		
		int minuteColumn = 4, minuteRow = 3;
		widgets.add(new XCalendarSelectTodayWidget(popup));
		widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		
		for (int row = 0; row < minuteRow; row++)
			for (int col = 0; col < minuteColumn; col++)
				widgets.add(new XCalendarMinuteWidget(popup, col, row));

		out.put(XCalendarState.MINUTE, widgets);
	}
	
	private static void second(Map<XCalendarState, List<XCalendarWidget>> out, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		
		int secondColumn = 4, secondRow = 3;
		widgets.add(new XCalendarSelectTodayWidget(popup));
		widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		
		for (int row = 0; row < secondRow; row++)
			for (int col = 0; col < secondColumn; col++)
				widgets.add(new XCalendarSecondWidget(popup, col, row));

		out.put(XCalendarState.SECOND, widgets);
	}
	
	/**
	 * 
	 */
	private static void day(Map<XCalendarState, List<XCalendarWidget>> out, List<XCalendarWidget> headers, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		widgets.addAll(headers);
		
		int dayColumn = 7,dayRow = 6;
		for (int col = 0; col < dayColumn; col++)
			widgets.add(new XCalendarTextWidget(popup, col));
		
		for (int row = 0; row < dayRow; row++) 
			for (int col = 0; col < dayColumn; col++)
				widgets.add(new XCalendarDayWidget(popup, col, row));

		widgets.add(new XCalendarSelectTodayWidget(popup));
		if (model.isTime()) widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		out.put(XCalendarState.DATE, widgets);
	}
	
	private static void month(Map<XCalendarState, List<XCalendarWidget>> out, List<XCalendarWidget> headers, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		widgets.addAll(headers);
		
		int monthColumn = 4, monthRow = 3;
		for (int row = 0; row < monthRow; row++)
			for (int col = 0; col < monthColumn; col++)
				widgets.add(new XCalendarMonthWidget(popup, col, row));
		
		widgets.add(new XCalendarSelectTodayWidget(popup));
		if (model.isTime()) widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		out.put(XCalendarState.MONTH, widgets);
	}

	private static void year(Map<XCalendarState, List<XCalendarWidget>> out, List<XCalendarWidget> headers, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		widgets.addAll(headers);
		
		int yearColumn = 4, yearRow = 3;
		for (int row = 0; row < yearRow; row++)
			for (int col = 0; col < yearColumn; col++)
				widgets.add(new XCalendarYearWidget(popup, col, row));
		
		widgets.add(new XCalendarSelectTodayWidget(popup));
		if (model.isTime()) widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		out.put(XCalendarState.YEAR, widgets);
	}
	
	private static void decade(Map<XCalendarState, List<XCalendarWidget>> out, List<XCalendarWidget> headers, XCalendar popup) {
		XCalendarModel model = popup.getModel();
		List<XCalendarWidget> widgets = new LinkedList<>();
		widgets.addAll(headers);
		
		int decadeColumn = 4, decadeRow = 3;
		for (int row = 0; row < decadeRow; row++)
			for (int col = 0; col < decadeColumn; col++)
				widgets.add(new XCalendarDecadeWidget(popup, col, row));
		
		widgets.add(new XCalendarSelectTodayWidget(popup));
		if (model.isTime()) widgets.add(new XCalendarSelectTimeWidget(popup));
		if (model.isNullable()) widgets.add(new XCalendarClearWidget(popup));
		out.put(XCalendarState.DECADE, widgets);
	}
}
