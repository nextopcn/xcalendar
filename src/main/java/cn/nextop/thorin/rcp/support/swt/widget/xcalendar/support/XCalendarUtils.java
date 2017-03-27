package cn.nextop.thorin.rcp.support.swt.widget.xcalendar.support;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.function.Predicate;

import static java.time.LocalDateTime.from;

/**
 * 
 * @author Baoyi Chen
 */
public final class XCalendarUtils {
	//
	public static final Integer[] DECADE_OFFSET = new Integer[] { 
			0, 12, 24, 36, 48, 60, 72, 84,  96, null, null, null };

	/**
	 * 
	 */
	public static ZonedDateTime now(ZoneId z) {
		return ZonedDateTime.now(z);
	}
	
	/**
	 * 
	 */
	public static Date som(YearMonth ym, ZoneId zid) {
		return toDate(ym.atDay(1).atStartOfDay(zid));
	}
	
	public static Date eom(YearMonth ym, ZoneId zid) {
		return toDate(ym.atEndOfMonth().atStartOfDay(zid));
	}
	
	public static Date som(int y, int m, ZoneId zid) {
		return toDate(YearMonth.of(y, m).atDay(1).atStartOfDay(zid));
	}
	
	public static Date eom(int y, int m, ZoneId zid) {
		return toDate(YearMonth.of(y, m).atEndOfMonth().atStartOfDay(zid));
	}
	
	public static Date soy(final int y, final ZoneId zid) {
		return toDate(YearMonth.of(y, 1).atDay(1).atStartOfDay(zid));
	}
	
	public static Date eoy(final int y, final ZoneId zid) {
		return toDate(YearMonth.of(y, 12).atEndOfMonth().atStartOfDay(zid));
	}
	
	/**
	 * 
	 */
	public static Instant truncatedTo(Date d, TemporalUnit unit) {
		return d.toInstant().truncatedTo(unit);
	}
	
	public static Instant truncatedTo(Instant d, TemporalUnit unit) {
		return d.truncatedTo(unit);
	}
	
	public static Instant truncatedTo(ZonedDateTime d, TemporalUnit unit) {
		return d.truncatedTo(unit).toInstant();
	}
	
	/**
	 * 
	 */
	public static boolean isSameYear(int y1, int y2) {
		return y1 == y2;
	}
	
	public static boolean isSameYear(Date d1, Date d2) {
		return isSameYear(from(d1.toInstant()), from(d2.toInstant()));
	}
	
	public static boolean isSameYear(ZonedDateTime zdt1, ZonedDateTime zdt2) {
		return zdt1.getYear() == zdt2.getYear();
	}
	
	public static boolean isSameYear(LocalDateTime ldt1, LocalDateTime ldt2) {
		return ldt1.getYear() == ldt2.getYear();
	}
	
	/**
	 * 
	 */
	public static boolean isSameYearMonth(Date d1, Date d2) {
		return isSameYearMonth(from(d1.toInstant()), from(d2.toInstant()));
	}
	
	public static boolean isSameYearMonth(ZonedDateTime d1, ZonedDateTime d2) {
		return d1.getYear() == d2.getYear() && d1.getMonthValue() == d2.getMonthValue();
	}
	
	public static boolean isSameYearMonth(LocalDateTime d1, LocalDateTime d2) {
		return d1.getYear() == d2.getYear() && d1.getMonthValue() == d2.getMonthValue();
	}
	
	/**
	 * 
	 */
	public static boolean isSameDate(Date d1, Date d2) {
		return isSameDate(from(d1.toInstant()), from(d2.toInstant()));
	}

	public static boolean isSameDate(ZonedDateTime d1, ZonedDateTime d2) {
		return d1.truncatedTo(ChronoUnit.DAYS).compareTo(d2.truncatedTo(ChronoUnit.DAYS)) == 0;
	}
	
	public static boolean isSameDate(LocalDateTime d1, LocalDateTime d2) {
		return d1.truncatedTo(ChronoUnit.DAYS).compareTo(d2.truncatedTo(ChronoUnit.DAYS)) == 0;
	}
	
	/**
	 * 
	 */
	public static boolean isValidDate(Date d, Predicate<Date> test) {
		return test.test(d);
	}
	
	public static boolean isValidYear(final int y, final ZoneId zid, Predicate<Date> test) {
		LocalDate soy = toDateTime(soy(y, zid), zid).toLocalDate();
		LocalDate eoy = toDateTime(eoy(y, zid), zid).toLocalDate();
		return isValid(soy, eoy, zid, test);
	}

	public static boolean isValidYearMonth(int y, int m, ZoneId zid, Predicate<Date> test) {
		LocalDate som = toDateTime(som(y, m, zid), zid).toLocalDate();
		LocalDate eom = toDateTime(eom(y, m, zid), zid).toLocalDate();
		return isValid(som, eom, zid, test);
	}
	
	public static boolean isValid(LocalDate ld1, LocalDate ld2, ZoneId zid, Predicate<Date> test) {
		while (ld1.compareTo(ld2) != 0) {
			if (test.test(toDate(ld1.atStartOfDay(zid)))) return true; else ld1 = ld1.plusDays(1);
		}
		return false;
	}
	
	/**
	 * 
	 */
	public static Date toDate(Instant v) {
		return Date.from(v);
	}

	public static Date toDate(ZonedDateTime v) {
		return Date.from(v.toInstant());
	}
	
	public static ZonedDateTime toDateTime(Date v, ZoneId z) {
		return toDateTime(v.toInstant(), z);
	}

	public static ZonedDateTime toDateTime(Instant v, ZoneId z) {
		return ZonedDateTime.ofInstant(v, z);
	}
	
	public static Date toDate(int y, int m, int d, int hh, int mm, int ss, int nano, ZoneId z) {
		return toDate(ZonedDateTime.of(y, m, d, hh, mm, ss, nano, z));
	}

	public static ZonedDateTime toDateTime(int y, int m, int d, int hh, int mm, int ss, int nano, ZoneId z) {
		return ZonedDateTime.of(y, m, d, hh, mm, ss, nano, z);
	}
}
