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
package com.ckmates.java.practice.example2.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ckmates.java.practice.example2.model.QSimpleMemo;
import com.ckmates.java.practice.example2.model.SimpleMemo;

@Repository
public interface SimpleMemoRepository
    // TODO
    extends PagingAndSortingRepository<SimpleMemo, Long>,
    QuerydslPredicateExecutor<SimpleMemo> {

  // TODO
  Page<SimpleMemo> findAllByArchived(boolean archived, Pageable pageable);

  // TODO
  default Page<SimpleMemo> findAllByArchivedAndLabelsIn(boolean archived,
      Collection<String> labels, Pageable pageable) {
    var qSimpleMemo = QSimpleMemo.simpleMemo;
    var archivedEq = qSimpleMemo.archived.eq(archived);
    var labelsAny = qSimpleMemo.labels.any().in(labels);

    return findAll(archivedEq.and(labelsAny), pageable);
  }

}
