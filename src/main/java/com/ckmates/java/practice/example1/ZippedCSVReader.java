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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.input.BOMInputStream;

import com.opencsv.CSVReader;

public final class ZippedCSVReader {

  private ZippedCSVReader() {}

  public static CSVReader zipToCSVReader(File zip) throws IOException {
    @SuppressWarnings("resource")
    ZipFile zipFile = new ZipFile(zip);
    ZipEntry csv = zipFile.entries().nextElement();
    BOMInputStream bOMInputStream =
        new BOMInputStream(zipFile.getInputStream(csv));
    bOMInputStream.getBOM();

    var reader = new InputStreamReader(new BufferedInputStream(bOMInputStream));
    return new CSVReader(reader);
  }

}
