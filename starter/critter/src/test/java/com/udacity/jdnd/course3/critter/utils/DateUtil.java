package com.udacity.jdnd.course3.critter.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;

public class DateUtil {
  public static LocalDate getDateByDow(int year, int weekNumber) {
    return LocalDate.of(year, 2, 1)
        .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
        .with(ChronoField.DAY_OF_WEEK, DayOfWeek.SUNDAY.getValue());
  }
}
