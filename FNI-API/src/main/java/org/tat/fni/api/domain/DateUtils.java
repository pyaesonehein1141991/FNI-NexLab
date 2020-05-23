package org.tat.fni.api.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.joda.time.chrono.GregorianChronology;
import org.tat.fni.api.common.emumdata.MonthType;


public class DateUtils {
  private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

  public static String formatDateToString(Date date) {
    return formatter.format(date);
  }

  public static Date formatStringToDate(String string) {
    Date date = null;
    try {
      date = formatter.parse(string);
    } catch (ParseException e) {
    }
    return date;
  }

  public static Date formatDate(Date date) {
    Date result = null;
    try {
      result = formatter.parse(formatter.format(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static int getYearFromDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    return year;
  }

  public static int getMonthFromDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int month = cal.get(Calendar.MONTH);
    return month;
  }

  public static int getDayFromDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(Calendar.DATE);
    return day;
  }

  public static String getHtmlDayString(Date date) {
    String result = "";
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int day = cal.get(Calendar.DATE);
    switch (day - (day / 10) * 10) {
      case 1:
        result += day + "<sup>st</sup>";
        break;
      case 2:
        result += day + "<sup>nd</sup>";
        break;
      case 3:
        result += day + "<sup>rd</sup>";
        break;
      default:
        result += day + "<sup>th</sup>";
        break;
    }
    return result;
  }

  public static SimpleDateFormat getFormatter() {
    return formatter;
  }

  public static String getDateFormatString(Date date) {
    return formatter.format(date);
  }

  public static Date addYears(Date date, int years) {
    return org.apache.commons.lang3.time.DateUtils.addYears(date, years);
  }

  /**
   * @param month : if month = 0, it is January. If month = 11, it is December.
   */
  public static Date getStartDateOfMonth(int month, int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    return cal.getTime();
  }

  /**
   * @param month : if month = 0, it is January. If month = 11, it is December.
   */
  public static Date getEndDateOfMonth(int month, int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    DateTime dateTime = new DateTime(cal.getTime());
    DateTime lastTime = dateTime.dayOfMonth().withMaximumValue();
    return new Date(lastTime.getMillis());
  }

  // Get Current Year
  public static int getCurrentYear() {
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.YEAR);
  }

  // Get Current Month
  public static int getCurrentMonth() {
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.MONTH);
  }

  public static List<Integer> getYearList() {
    List<Integer> yearList = new ArrayList<Integer>();
    int currentYear = DateUtils.getCurrentYear();
    for (int x = currentYear; x > currentYear - 11; x--) {
      yearList.add(x);
    }
    return yearList;
  }

  public static List<Integer> getToYearList(int fromYear) {
    List<Integer> toYearList = new ArrayList<Integer>();
    int currentYear = DateUtils.getCurrentYear();
    for (int x = currentYear + 1; x >= fromYear; x--) {
      toYearList.add(x);
    }
    return toYearList;
  }

  public static int ageFromDateOfBirth(Date dateOfBirth) {
    if (dateOfBirth == null)
      return 0;
    LocalDate birthdate = new LocalDate(dateOfBirth);
    LocalDate now = new LocalDate();
    Years age = Years.yearsBetween(birthdate, now);
    return age.getYears();
  }

  public static Date resetStartDate(Date startDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date resetEndDate(Date endDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(endDate);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    return cal.getTime();
  }

  /**
   * Subtract day from given date.
   * 
   * @param date
   * @param day
   * @return date that is subtracted by given day
   */
  public static Date minusDays(Date date, int day) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.minusDays(day).toDate();
  }

  /**
   * Add day from given date.
   * 
   * @param date
   * @param day
   * @return date that is added by given day
   */
  public static Date plusDays(Date date, int day) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.plusDays(day).toDate();
  }

  /**
   * Calculate number of days between start date and end date.
   * 
   * @param startDate
   * @param endDate
   * @param isTimeInclude
   * @param isDayCount
   * @return number of days between start date and end date
   */
  public static int daysBetween(Date startDate, Date endDate, boolean isTimeInclude,
      boolean isDayCount) {
    // reset time in calculation if isTimeInclude is false
    if (!isTimeInclude) {
      startDate = resetStartDate(startDate);
      endDate = resetStartDate(endDate);
    }
    DateTime start = new DateTime(startDate);
    DateTime end = new DateTime(endDate);
    if (isDayCount) {
      end = end.plusDays(1);
    }
    return Days.daysBetween(start, end).getDays();
  }

  public static int monthsBetween(Date startDate, Date endDate, boolean isTimeInclude,
      boolean isDayCount) {
    // reset time in calculation if isTimeInclude is false
    if (!isTimeInclude) {
      startDate = resetStartDate(startDate);
      endDate = resetStartDate(endDate);
    }
    DateTime start = new DateTime(startDate);
    DateTime end = new DateTime(endDate);
    if (isDayCount) {
      end = end.plusDays(1);
    }
    return Months.monthsBetween(start, end).getMonths();
  }

  public static int yearsBetween(Date startDate, Date endDate, boolean isTimeInclude,
      boolean isDayCount) {
    // reset time in calculation if isTimeInclude is false
    if (!isTimeInclude) {
      startDate = resetStartDate(startDate);
      endDate = resetStartDate(endDate);
    }
    DateTime start = new DateTime(startDate);
    DateTime end = new DateTime(endDate);
    if (isDayCount) {
      end = end.plusDays(1);
    }
    return Years.yearsBetween(start, end).getYears();
  }

  /**
   * Calculate period between start date and end date.
   * 
   * @param startDate
   * @param endDate
   * @param isTimeInclude
   * @param isDayCount
   * @return period between start date and end date
   */
  public static Period getPeriod(Date startDate, Date endDate, boolean isTimeInclude,
      boolean isDayCount) {
    PeriodType periodType = PeriodType.yearMonthDay();
    if (isTimeInclude) {
      periodType = PeriodType.yearMonthDayTime();
    }
    LocalDateTime start = new LocalDateTime(startDate, DateTimeZone.getDefault());
    LocalDateTime end = new LocalDateTime(endDate, DateTimeZone.getDefault());
    if (isDayCount) {
      end = end.plusDays(1);
    }
    Period period = new Period(start, end, periodType);
    return period;
  }

  /**
   * Create date by given year, month and date.
   * 
   * @param year
   * @param month
   * @param date
   * @return date of given year, month and date.
   */
  public static Date createDate(int year, int month, int date) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month - 1, date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * Return number of days in given year and month.
   * 
   * @param year
   * @param month
   * @return number of days by given year and month
   */
  public static int getDaysNumber(int year, int month) {
    Date date = createDate(year, month, 1);
    return GregorianChronology.getInstance(DateTimeZone.getDefault()).dayOfMonth()
        .getMaximumValue(date.getTime());
  }

  public static boolean isDateBetween(Date startDate, Date endDate, Date checkDate) {
    return checkDate.after(startDate) && checkDate.before(endDate);
  }

  /**
   * Return the index of Maximum date.
   * 
   * @param DateList
   * @return index of maximum date.
   */
  public static int getIndexOfMaxDate(List<Date> dateList) {
    Date maxDate = null;
    if (dateList.size() == 1) {
      maxDate = dateList.get(0);
    } else {
      maxDate = dateList.get(0);
      for (Date d : dateList) {
        if (d.after(maxDate)) {
          maxDate = d;
        }
      }
    }
    return dateList.indexOf(maxDate);
  }

  /**
   * @param stringDate : format must be dd-MM-yyyy
   */
  public static Date getDate(String stringDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = null;
    try {
      date = formatter.parse(stringDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  public static boolean isAfter(Date date1, Date date2) {
    return date1.after(date2);
  }

  public static boolean isBefore(Date date1, Date date2) {
    return date1.before(date2);
  }

  public static int getDayOfYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.MONTH, 11);
    cal.set(Calendar.DAY_OF_MONTH, 31);
    DateTime dateTime = new DateTime(cal.getTime(), DateTimeZone.getDefault());
    return dateTime.getDayOfYear();
  }

  /**
   * 
   * @param year
   * @param month
   * @return java.util.Date
   */
  public static Date getStartDate(int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.YEAR, year);
    Date starDate = calendar.getTime();
    return DateUtils.resetStartDate(starDate);
  }

  /**
   * 
   * @param year
   * @param month
   * @return java.util.Date
   */
  public static Date getEndDate(int year, int month) {
    Calendar calendar2 = Calendar.getInstance();
    calendar2.set(Calendar.MONTH, month);
    calendar2.set(Calendar.YEAR, year);
    DateTime dateTime = new DateTime(calendar2.getTime(), DateTimeZone.getDefault());
    calendar2.set(Calendar.DAY_OF_MONTH, dateTime.dayOfMonth().withMaximumValue().getDayOfMonth());
    Date endDate = calendar2.getTime();
    return DateUtils.resetEndDate(endDate);
  }

  /**
   * @param if month is 0, "JANUARY" will returned.
   */
  public static String getMonthString(int month) {
    if (month == 0) {
      return "JANUARY";
    }
    if (month == 1) {
      return "FEBRUARY";
    }
    if (month == 2) {
      return "MARCH";
    }
    if (month == 3) {
      return "APRIL";
    }
    if (month == 4) {
      return "MAY";
    }
    if (month == 5) {
      return "JUNE";
    }
    if (month == 6) {
      return "JULY";
    }
    if (month == 7) {
      return "AUGUST";
    }
    if (month == 8) {
      return "SEPTEMBER";
    }
    if (month == 9) {
      return "OCTOBER";
    }
    if (month == 10) {
      return "NOVEMBER";
    }
    if (month == 11) {
      return "DECEMBER";
    }
    return null;
  }

  /**
   * Calculate age for next year.
   * 
   * @param dateOfBirth
   * @return age for next year
   */
  public static int getAgeForNextYear(Date dateOfBirth) {
    Calendar cal_1 = Calendar.getInstance();
    int currentYear = cal_1.get(Calendar.YEAR);
    Calendar cal_2 = Calendar.getInstance();
    cal_2.setTime(dateOfBirth);
    cal_2.set(Calendar.YEAR, currentYear);

    if (new Date().after(cal_2.getTime())) {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR) + 1;
      return year_2 - year_1;
    } else {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR);
      return year_2 - year_1;
    }
  }

  /**
   * Calculate age for next year.
   * 
   * @param dateOfBirth
   * @return age for next year
   */
  public static int getAgeForNextYear(Date dateOfBirth, Date calculateOnDate) {
    Calendar cal_1 = Calendar.getInstance();
    cal_1.setTime(calculateOnDate);
    int currentYear = cal_1.get(Calendar.YEAR);
    Calendar cal_2 = Calendar.getInstance();
    cal_2.setTime(dateOfBirth);
    cal_2.set(Calendar.YEAR, currentYear);

    if (new Date().after(cal_2.getTime())) {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR) + 1;
      return year_2 - year_1;
    } else {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR);
      return year_2 - year_1;
    }
  }

  public static int getAgeForOldYear(Date commDate, Date dateOfBirth) {
    Calendar cal_1 = Calendar.getInstance();
    cal_1.setTime(commDate);
    int currentYear = cal_1.get(Calendar.YEAR);
    Calendar cal_2 = Calendar.getInstance();
    cal_2.setTime(dateOfBirth);
    cal_2.set(Calendar.YEAR, currentYear);

    if (commDate.after(cal_2.getTime())) {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR) + 1;
      return year_2 - year_1;
    } else {
      Calendar cal_3 = Calendar.getInstance();
      cal_3.setTime(dateOfBirth);
      int year_1 = cal_3.get(Calendar.YEAR);
      int year_2 = cal_1.get(Calendar.YEAR);
      return year_2 - year_1;
    }
  }

  public static String getAge(Date dob) {
    Period period = getPeriod(dob);
    return period.getYears() + " Y";
  }

  public static Period getPeriod(Date date) {
    DateTime startDate = new DateTime(date);
    Calendar cal = Calendar.getInstance();
    Date todayDate = cal.getTime();
    DateTime endDate = new DateTime(todayDate);
    Period period = new Period(startDate, endDate);
    return period;
  }

  /**
   * Calculate month.main
   * 
   * 
   * @param MonthType
   * @return integer : if MomthType=January , return 0. If MomthType=February, return 1.
   */
  public static int getMonthNumber(MonthType month) {
    int result = 0;
    switch (month) {
      case JAN:
        result = 0;
        break;
      case FEB:
        result = 1;
        break;
      case MAR:
        result = 2;
        break;
      case APR:
        result = 3;
        break;
      case MAY:
        result = 4;
        break;
      case JUN:
        result = 5;
        break;
      case JUL:
        result = 6;
        break;
      case AUG:
        result = 7;
        break;
      case SEP:
        result = 8;
        break;
      case OCT:
        result = 9;
        break;
      case NOV:
        result = 10;
        break;
      case DEC:
        result = 11;
        break;
      default:
        break;
    }
    return result;
  }

  public static Date addMonth(Date date, int month) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.plusMonths(month).toDate();
  }

  public static Date substractMonth(Date date, int month) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.minusMonths(month).toDate();
  }

  public static Date addDay(Date date, int day) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.plusDays(day).toDate();
  }

  public static Date substractDay(Date date, int day) {
    DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
    return dateTime.minusDays(day).toDate();
  }

  public static int daysOfFinancialYear(Date paramDate) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(paramDate);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    LocalDate localDate = null;
    if (month == Calendar.JANUARY || month == Calendar.FEBRUARY || month == Calendar.MARCH) {
      localDate = new LocalDate(year - 1, Calendar.APRIL, 1);
    } else {
      localDate = new LocalDate(year, Calendar.APRIL, 1);
    }
    int numberOfDays = Days.daysBetween(localDate, localDate.plusMonths(12)).getDays();
    return numberOfDays;
  }

  public static <T> boolean isNCB(Date policyEndDate) {
    Date fromDate = substractMonth(policyEndDate, 2);
    fromDate = substractDay(fromDate, 15);
    Date currentDate = new Date();
    Date toDate = addMonth(policyEndDate, 1);
    toDate = addDay(toDate, 1);
    fromDate = DateUtils.resetStartDate(fromDate);
    toDate = DateUtils.resetEndDate(toDate);
    if (fromDate.before(currentDate) && currentDate.before(toDate)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param Date : if Date, return Date eg. 12-30-2000.
   */
  public static String formattedDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(date);
  }

  public static String formattedSqlDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  /**
   * Return true if the current date is before parameter date + 1 month
   * 
   * @param date
   * @return
   */
  public static <T> boolean isWithinOneMonth(Date date) {
    Date plusOneMonth = DateUtils.addMonth(date, 1);
    Date present = DateUtils.resetStartDate(new Date());
    if (present.compareTo(plusOneMonth) <= 0) {
      return true;
    }
    return false;
  }

  public static int getPeriodOfMonth(int period, PeriodType periodType) {
    int periodOfMonth = period;
    if (periodType.equals(periodType.days())) {
      periodOfMonth = period / 31;
    } else if (periodType.equals(periodType.years())) {
      periodOfMonth = period * 12;
    }
    return periodOfMonth;
  }
}
