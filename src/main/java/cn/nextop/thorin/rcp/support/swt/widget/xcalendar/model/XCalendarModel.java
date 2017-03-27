package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.XCalendarConfig;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarTheme;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.theme.XCalendarThemeFactory;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XVirtualCalendar;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.fsm.XCalendarStateMachine;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState;
import org.eclipse.swt.SWT;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.toDate;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.truncatedTo;
import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState.*;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarModel {
	//
	protected int style;
	protected Date date;
	protected ZoneId zoneId;
	protected boolean nullable;
	protected XCalendarTheme theme;
	protected XCalendarConfig config;
	protected XVirtualCalendar calendar;
	protected XCalendarStateMachine stateMachine;

	/**
	 * 
	 */
	public XCalendarModel(int style) {
		//
		this.style = style;
		this.config = new XCalendarConfig();
		this.zoneId = ZoneId.systemDefault();
		this.calendar = new XVirtualCalendar(this.zoneId, isTime());
		this.theme = new XCalendarThemeFactory().create(Locale.getDefault());
		
		//
		if (isTime()) {
			this.stateMachine = new XCalendarStateMachine(DATE, TIME);
		} else if (isDate()) {
			this.stateMachine = new XCalendarStateMachine(DATE, null);
		} else if (isYear()) {
			this.stateMachine = new XCalendarStateMachine(YEAR, null);
		} else if (isYearMonth()) {
			this.stateMachine = new XCalendarStateMachine(MONTH, null);
		} 
	}

	/**
	 * 
	 */
	public boolean isTime() {
		return (style & SWT.TIME) != 0;
	}

	public boolean isDate() {
		return (style & SWT.DATE) != 0;
	}

	public boolean isYear() {
		return (style & SWT.SHORT) != 0;
	}
	
	public boolean isYearMonth() {
		return (style & SWT.MEDIUM) != 0;
	}

	/**
	 * 
	 */
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public ZoneId getZoneId() {
		return zoneId;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
	}
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public XCalendarState getState() {
		return this.stateMachine.current();
	}
	
	public XCalendarStateMachine getStateMachine() {
		return this.stateMachine;
	}

	public final XCalendarTheme getTheme() {
		return theme;
	}

	public final void setTheme(XCalendarTheme theme) {
		this.theme = theme;
	}

	public XVirtualCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(XVirtualCalendar calendar) {
		this.calendar = calendar;
	}
	
	public final XCalendarConfig getConfig() {
		return config;
	}

	public final void setConfig(XCalendarConfig config) {
		this.config = config;
	}
	
	/**
	 * 
	 */
	public Date now() {
		if(!isTime()) {
			return toDate(truncatedTo(XCalendarUtils.now(getZoneId()), ChronoUnit.DAYS));
		} else {
			return toDate(truncatedTo(XCalendarUtils.now(getZoneId()), ChronoUnit.SECONDS));
		}
	}
}
