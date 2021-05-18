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
package com.ckmates.java.practice.example2.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ckmates.java.practice.example2.model.SimpleMemo;
import com.ckmates.java.practice.example2.repository.SimpleMemoRepository;

@Service
public class MemoService {

  @Autowired
  SimpleMemoRepository simpleMemoRepo;

  public Page<SimpleMemo> findAllByByArchivedAndLabelFilter(boolean archived,
      String labelFilter, Pageable pageable) {
    Page<SimpleMemo> page;

    if (labelFilter == null || labelFilter.isBlank()) {
      page = simpleMemoRepo.findAllByArchived(archived, pageable);
    } else {
      page = simpleMemoRepo.findAllByArchivedAndLabelsIn(archived,
          Arrays.asList(labelFilter.trim().split(",")), pageable);
    }

    return page;
  }

}
