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
package com.datastax.demo.common.repository;

import static com.datastax.demo.common.conf.StockQueriesConfiguration.DATE;
import static com.datastax.demo.common.conf.StockQueriesConfiguration.SYMBOL;
import static com.datastax.demo.common.conf.StockQueriesConfiguration.VALUE;

import com.datastax.demo.common.model.Stock;
import com.datastax.oss.driver.api.core.cql.Row;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.stereotype.Component;

/**
 * A row mapping function that creates a {@link Stock} instance from a database {@link Row}.
 *
 * <p>The row is expected to contain all 3 columns: symbol, date and value, as if it were obtained
 * by a CQL query such as {@code SELECT symbol, date, value FROM stocks}.
 */
@Component
public class StockRowMapper implements Function<Row, Stock> {

  @Override
  public Stock apply(Row row) {
    var symbol = Objects.requireNonNull(row.getString(SYMBOL));
    var date = Objects.requireNonNull(row.getInstant(DATE));
    var value = Objects.requireNonNull(row.getBigDecimal(VALUE));
    return new com.datastax.demo.common.model.Stock(symbol, date, value);
  }
}
