package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.fsm;

import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.model.config.widget.impl.*;
import cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState;

import javax.annotation.Nullable;

import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.glossary.XCalendarState.*;

/**
 * 
 * @author Baoyi Chen
 */
public class XCalendarStateMachine {
	//
	private XCalendarState curr, prev;
	
	/**
	 * 
	 */
	public XCalendarStateMachine(XCalendarState curr) {
		this(curr, null);
	}
	
	public XCalendarStateMachine(XCalendarState curr, @Nullable XCalendarState prev) {
		this.curr = curr; this.prev = prev;
	}
	
	/**
	 * 
	 */
	public XCalendarState current() {
		return curr; 
	}
	
	public void reset() {
		if(isTime()) this.curr = prev; this.prev = TIME;
	}
	
	public boolean isTime() {
		if(this.curr == TIME) return true;
		return curr == HOUR || curr == MINUTE || curr == SECOND;
	}
	
	/**
	 * 
	 */
	public void mutate(XCalendarSelectTimeWidget widget) {
		XCalendarState temp = this.curr;
		this.curr = this.prev; this.prev = temp;
	}

	public void mutate(final XCalendarTimeWidget widget) {
		switch (widget.getType()) {
		case HOUR: this.curr = XCalendarState.HOUR; break;
		case MINUTE: this.curr = XCalendarState.MINUTE; break;
		case SECOND: this.curr = XCalendarState.SECOND; break;
		default: throw new AssertionError("invalid widget: " + widget);
		}
	}
	
	public void mutate(final XCalendarSelectWidget widget) {
		switch (this.curr) {
		case DATE: this.curr = XCalendarState.MONTH; break;
		case MONTH: this.curr = XCalendarState.YEAR; break;
		case YEAR: this.curr = XCalendarState.DECADE; break;
		case DECADE: break;
		default: throw new AssertionError("invalid widget: " + widget);
		}
	}
	
	/**
	 * 
	 */
	public void mutate(XCalendarDecadeWidget widget) { this.curr = XCalendarState.YEAR; }
	public void mutate(XCalendarYearWidget widget)  { this.curr = XCalendarState.MONTH; }
	public void mutate(XCalendarMonthWidget widget)  { this.curr = XCalendarState.DATE; }
	public void mutate(XCalendarHourWidget widget)   { this.curr = XCalendarState.TIME; }
	public void mutate(XCalendarMinuteWidget widget) { this.curr = XCalendarState.TIME; }
	public void mutate(XCalendarSecondWidget widget) { this.curr = XCalendarState.TIME; }
}
