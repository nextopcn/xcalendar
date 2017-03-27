package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support;

import cn.nextop.thorin.rcp.support.util.type.Range;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Predicate;

import static cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support.XCalendarUtils.*;
import static cn.nextop.thorin.rcp.support.util.type.Range.closed;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * 
 * @author Baoyi Chen
 */
public class XVirtualCalendar {
	//
	private int decade;
	private int century;
	private Date prevDate;
	private int depth = 400;
	private final boolean time;
	private final ZoneId zoneId;
	private ZonedDateTime travel;
	private ZonedDateTime calendar;
	private Predicate<Date> predicate;
	
	/**
	 * 
	 */
	public XVirtualCalendar(ZoneId zoneId, boolean time) {
		this.zoneId = zoneId; this.time = time; this.predicate = (v) -> true; 
		adjust(toDate(truncatedTo(now(zoneId), DAYS))); this.prevDate = null;
	}
	
	/**
	 * 
	 */
	private void update() { updateDecade(); updateCentury(); }
	private void updateDecade() { decade = travel.getYear() / 10 * 10; }
	private void updateCentury() { this.century = this.decade / 100 * 100; }
	
	/**
	 * 
	 */
	public void prevCentury() { century -= 100; }
	public void prevDecade() { decade -= 10; updateCentury(); }
	public void prevYear() { travel = travel.minusYears(1); update(); }
	public void prevMonth() { travel = travel.minusMonths(1); update(); }
	public void prevHour() { this.calendar = this.calendar.minusHours(1); }
	public void prevMinute() { this.calendar = this.calendar.minusMinutes(1); }
	public void prevSecond() { this.calendar = this.calendar.minusSeconds(1); }
	
	/**
	 * 
	 */
	public void nextCentury() { century += 100; }
	public void nextDecade() { decade += 10; updateCentury(); }
	public void nextYear() { travel = travel.plusYears(1); update(); }
	public void nextMonth() { travel = travel.plusMonths(1); update(); }
	public void nextHour() { this.calendar = this.calendar.plusHours(1); }
	public void nextMinute() { this.calendar = this.calendar.plusMinutes(1); }
	public void nextSecond() { this.calendar = this.calendar.plusSeconds(1); }
	
	/**
	 * 
	 */
	public Date getDate() { return toDate(calendar); }
	public Date getTravel() { return toDate(travel); }
	public ZonedDateTime getTravelDateTime() { return travel; }
	public ZonedDateTime getCalendarDateTime() { return calendar; }
	public boolean isValid(Date date) { return predicate.test(date); }
	public void setFilter(Predicate<Date> filter) { this.predicate = filter; }
	
	/**
	 * 
	 */
	public int getYear() { return travel.getYear(); }
	public int getMonth() { return travel.getMonthValue(); }
	public int getHour() { return this.calendar.getHour(); }
	public int getMinute() { return this.calendar.getMinute(); }
	public int getSecond() { return this.calendar.getSecond(); }
	public Range<Integer> getDecade() { return closed(decade, decade + 11); }
	public Range<Integer> getCentury() { return closed(century, century + 107); }
	
	/**
	 * 
	 */
	public void setCentury(int century) { this.century = century; }
	public void setDecade(int decade) { this.decade = decade; updateCentury(); }
	public void setYear(int year) { travel = travel.withYear(year); update(); }
	public void setMonth(int month) { travel = travel.withMonth(month); update(); }
	public void setHour(int hour ) { this.calendar = this.calendar.withHour(hour); }
	public void setMinute(int minute) { this.calendar = this.calendar.withMinute(minute); }
	public void setSecond(int second) { this.calendar = this.calendar.withSecond(second); }
	
	/**
	 * 
	 */
	public Date adjust(Date date) { 
		return adjust(date, false); 
	}
	
	public Date adjust(Date date, boolean clear) {
		//
		Date currDate = null;
		if (!this.time || clear) this.prevDate = null;
		this.travel = toDateTime(truncatedTo(date, ChronoUnit.SECONDS), zoneId);
		this.calendar = toDateTime(truncatedTo(date, ChronoUnit.SECONDS), zoneId);
		
		// +1 direction
		ZonedDateTime next = this.travel;
		for (int i = 0; i < depth && !isValid(currDate = toDate(next)); i++) 
			next = next.plusDays(prevDate == null ? 1 : !prevDate.after(currDate) ? 1 : -1);
		if (isValid(toDate(next))) {
			this.travel = next; this.calendar = next; prevDate = currDate; update(); return toDate(calendar);
		}
		
		// -1 direction
		ZonedDateTime prev = this.travel;
		for (int i = 0; i < depth && !isValid(currDate = toDate(prev)); i++) 
			prev = prev.plusDays(prevDate == null ? -1 : !prevDate.after(currDate) ? 1 : -1);
		if (isValid(toDate(prev))) {
			this.travel = prev; this.calendar = prev; prevDate = currDate; update(); return toDate(calendar);
		}
		return adjust(prevDate);
	}
}
