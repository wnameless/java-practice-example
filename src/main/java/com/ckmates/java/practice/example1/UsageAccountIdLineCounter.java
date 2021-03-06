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
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.rubycollect4j.Ruby;
import net.sf.rubycollect4j.RubyIO.Mode;

public class UsageAccountIdLineCounter {

  public static void main(String... strings)
      throws InterruptedException, ExecutionException, JsonProcessingException {
    var startTime = System.currentTimeMillis();

    var tasks = new ArrayList<CompletableFuture<Map<String, Integer>>>();

    File curFolder = new File("../amazon-billing/CUR");
    for (var zip :
    // TODO
    FileUtils.listFiles(curFolder, new String[] { "zip" }, true)) {
      var task = new AsyncUsageAccountIdLineCounter(zip).countAsync();
      tasks.add(task);
    }

    // Wait for async tasks
    // TODO
    CompletableFuture.allOf(tasks.stream().toArray(CompletableFuture[]::new))
        .join();

    // TODO
    var result = new TreeMap<String, Integer>();
    for (var task : tasks) {
      var counts = task.get();
      // TODO
      counts.forEach(
          (key, value) -> result.merge(key, value, (v1, v2) -> v1 + v2));
    }

    System.out.println(System.currentTimeMillis() - startTime);
    System.out.println(result);

    var json = Ruby.File.open("result.json", Mode.W);
    json.puts(new ObjectMapper().writeValueAsString(result));
  }

}
