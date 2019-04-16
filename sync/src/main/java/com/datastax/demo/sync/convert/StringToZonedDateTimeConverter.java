/*
 * Copyright DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.demo.sync.convert;

import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

  private static final DateTimeFormatter PARSER =
      new DateTimeFormatterBuilder()
          .appendValue(YEAR_OF_ERA, 4)
          .optionalStart()
          .appendValue(MONTH_OF_YEAR, 2)
          .optionalStart()
          .appendValue(DAY_OF_MONTH, 2)
          .optionalStart()
          .appendLiteral('-')
          .appendValue(HOUR_OF_DAY, 2)
          .optionalStart()
          .appendValue(MINUTE_OF_HOUR, 2)
          .optionalStart()
          .appendValue(SECOND_OF_MINUTE, 2)
          .optionalStart()
          .appendValue(MILLI_OF_SECOND, 3)
          .optionalEnd()
          .optionalEnd()
          .optionalEnd()
          .optionalEnd()
          .optionalEnd()
          .optionalEnd()
          .optionalStart()
          .appendOffset("+HHmm", "Z") // matches +0200 and +02
          .optionalEnd()
          .parseDefaulting(MONTH_OF_YEAR, 1)
          .parseDefaulting(DAY_OF_MONTH, 1)
          .parseDefaulting(HOUR_OF_DAY, 0)
          .parseDefaulting(MINUTE_OF_HOUR, 0)
          .parseDefaulting(SECOND_OF_MINUTE, 0)
          .parseDefaulting(MILLI_OF_SECOND, 0)
          .toFormatter()
          .withZone(UTC);

  @Override
  public ZonedDateTime convert(@NonNull String source) {
    return ZonedDateTime.from(PARSER.parse(source));
  }
}
