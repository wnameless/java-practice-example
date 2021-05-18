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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ckmates.java.practice.example2.controller.form.MemoForm;
import com.ckmates.java.practice.example2.model.SimpleMemo;
import com.ckmates.java.practice.example2.repository.SimpleMemoRepository;

@RequestMapping("/memos")
@Controller
public class MemoController {

  @Autowired
  SimpleMemoRepository simpleMemoRepo;
  @Autowired
  MemoService memoService;

  Pageable pageable;
  boolean archived;
  String labelFilter;

  // TODO
  @ModelAttribute("labelFilter")
  String labelFilter(
      @RequestParam(required = false, defaultValue = "") String labelFilter) {
    return this.labelFilter = labelFilter;
  }

  // TODO
  @ModelAttribute("pageable")
  Pageable pageable(@SortDefault(sort = "timestamp",
      direction = Direction.DESC) Pageable pageable) {
    return this.pageable = pageable;
  }

  // TODO
  @ModelAttribute("archived")
  boolean showArchived(@RequestParam(required = false) Boolean archived) {
    return this.archived = archived != null ? archived : false;
  }

  @GetMapping
  String index(Model model) {
    model.addAttribute("memo", new SimpleMemo());
    model.addAttribute("page", memoService
        .findAllByByArchivedAndLabelFilter(archived, labelFilter, pageable));
    return "memos/index";
  }

  @PostMapping
  String create(Model model,
      // TODO
      @Valid @ModelAttribute MemoForm memoForm,
      // TODO
      BindingResult bindingResult) {
    SimpleMemo memo;
    // TODO
    if (bindingResult.hasErrors()) {
      model.addAttribute("message",
          bindingResult.getAllErrors().get(0).getDefaultMessage());
      memo = new SimpleMemo(memoForm);
      model.addAttribute("memo", memo);
    } else {
      memo = simpleMemoRepo.save(new SimpleMemo(memoForm));
      model.addAttribute("memo", new SimpleMemo());
    }

    model.addAttribute("page", memoService
        .findAllByByArchivedAndLabelFilter(archived, labelFilter, pageable));
    return "memos/index";
  }

  @GetMapping("/{id}")
  String read(Model model, @PathVariable Long id) {
    SimpleMemo memo = simpleMemoRepo.findById(id).get();

    model.addAttribute("memo", memo);
    model.addAttribute("page", memoService
        .findAllByByArchivedAndLabelFilter(archived, labelFilter, pageable));
    return "memos/index";
  }

  @PostMapping(path = "/{id}",
      // TODO
      params = "update")
  String update(Model model, @PathVariable Long id,
      // TODO
      @Valid @ModelAttribute MemoForm memoForm,
      // TODO
      BindingResult bindingResult) {
    SimpleMemo memo = simpleMemoRepo.findById(id).get();
    memo = new SimpleMemo(memoForm);
    // TODO
    memo.setId(id);

    if (bindingResult.hasErrors()) {
      // TODO
      model.addAttribute("message",
          bindingResult.getAllErrors().get(0).getDefaultMessage());
      model.addAttribute("memo", memo);
    } else {
      memo = simpleMemoRepo.save(memo);
      model.addAttribute("memo", new SimpleMemo());
    }

    model.addAttribute("page", memoService
        .findAllByByArchivedAndLabelFilter(archived, labelFilter, pageable));
    return "memos/index";
  }

  @PostMapping(path = "/{id}",
      // TODO
      params = "delete")
  String delete(Model model, @PathVariable Long id) {
    SimpleMemo memo = simpleMemoRepo.findById(id).get();
    simpleMemoRepo.delete(memo);

    model.addAttribute("memo", new SimpleMemo());
    model.addAttribute("page", memoService
        .findAllByByArchivedAndLabelFilter(archived, labelFilter, pageable));
    return "memos/index";
  }

}
