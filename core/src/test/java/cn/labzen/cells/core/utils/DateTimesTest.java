package cn.labzen.cells.core.utils;

import cn.labzen.cells.core.utils.DateTimes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

class DateTimesTest {

  @Test
  void test() {
    ZoneOffset offset = OffsetDateTime.now().getOffset();

    Date now = new Date();
    Calendar instance = Calendar.getInstance();
    instance.setTime(now);

    LocalDateTime localDateTime = DateTimes.toLocalDateTime(now);
    Assertions.assertNotNull(localDateTime);
    Assertions.assertEquals(now.getTime(), localDateTime.toInstant(offset).toEpochMilli());

    LocalDate localDate = DateTimes.toLocalDate(now);
    Assertions.assertNotNull(localDate);
    Assertions.assertEquals(instance.get(Calendar.DAY_OF_YEAR), localDate.getDayOfYear());

    LocalTime localTime = DateTimes.toLocalTime(now);
    Assertions.assertNotNull(localTime);
    Assertions.assertEquals(instance.get(Calendar.MINUTE), localTime.getMinute());

    Date date1 = DateTimes.toDate(localDateTime);
    Date date2 = DateTimes.toDate(localDate, localTime);
    Assertions.assertEquals(date1, date2);
  }
}
