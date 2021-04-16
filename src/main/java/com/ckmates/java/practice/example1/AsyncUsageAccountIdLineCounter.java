/*
 *
 * Copyright 2021 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.ckmates.java.practice.example1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import net.sf.rubycollect4j.Ruby;

public final class AsyncUsageAccountIdLineCounter {

  private final Map<String, Integer> counter = new HashMap<>();
  private final File zip;

  public AsyncUsageAccountIdLineCounter(File zip) {
    this.zip = Objects.requireNonNull(zip);
  }

  public CompletableFuture<Map<String, Integer>> countAsync() {
    return CompletableFuture.supplyAsync(() -> {
      try (CSVReader csvReader = ZippedCSVReader.zipToCSVReader(zip)) {
        if (csvReader.peek() != null) {
          var header = csvReader.readNext();
          var uaIdIdx =
              Ruby.Array.copyOf(header).index("lineItem/UsageAccountId");

          while (uaIdIdx != null && csvReader.peek() != null) {
            var line = csvReader.readNext();
            var uaId = line[uaIdIdx];
            if (counter.containsKey(uaId)) {
              counter.put(uaId, counter.get(uaId) + 1);
            } else {
              counter.put(uaId, 1);
            }
          }
        }
      } catch (CsvValidationException | IOException e) {
        throw new RuntimeException(e);
      }

      return counter;
    });
  }

}
