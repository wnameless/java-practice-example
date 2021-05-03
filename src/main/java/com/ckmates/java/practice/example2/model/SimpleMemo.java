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
package com.ckmates.java.practice.example2.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.ckmates.java.practice.example2.controller.form.MemoForm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Entity
public class SimpleMemo implements Memo {

  @GeneratedValue
  @Id
  Long id;

  String title;

  String text;

  LocalDateTime timestamp = LocalDateTime.now();

  // TODO
  @ElementCollection
  Set<String> labels = new TreeSet<>();

  boolean archived = false;

  public SimpleMemo() {}

  // TODO
  public SimpleMemo(MemoForm memoForm) {
    title = memoForm.getTitle();
    text = memoForm.getText();
    if (memoForm.getLabels() != null) {
      for (var label : memoForm.getLabels().split(",")) {
        labels.add(label.trim());
      }
    }
    archived = memoForm.isArchived();
  }

}
