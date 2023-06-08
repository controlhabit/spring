package com.example.controlhabitspring.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import com.example.controlhabitspring.domain.User;
import com.example.controlhabitspring.domain.UserSpecs.SearchKey;
import com.example.controlhabitspring.service.UserService;

import com.example.controlhabitspring.model.BasicResponse;
import com.example.controlhabitspring.model.CommonResponse;
import com.example.controlhabitspring.model.ErrorResponse;

import org.apache.commons.lang3.StringUtils;


@RestController
@RequestMapping("/api/user")
public class UserController {

    Logger LOG = Logger.getGlobal();

    @Autowired
    UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> findById(@PathVariable String id) {
        Optional<User> opt = service.findById(id);

        if(!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("데이터를 찾을수가 없습니다."));
        }

        User item = opt.get();
        item.setUserPw("");
        
        return ResponseEntity.ok().body(new CommonResponse<User>(item));
    }

    @GetMapping("")
    public ResponseEntity<? extends BasicResponse> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                                           @RequestParam(value = "orderby", required = false) String orderby,
                                                           @RequestParam(value = "id", required = false) String id,
                                                           @RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "email", required = false) String email) {
        if (page > 0) {
            page--;
        }
        
        Map<SearchKey, Object> searchKeys = new HashMap<>();
        if (!StringUtils.isEmpty(id)) {
            searchKeys.put(SearchKey.valueOf("ID"), id);
        }

        if (!StringUtils.isEmpty(name)) {
            searchKeys.put(SearchKey.valueOf("NAME"), name);
        }

        if (!StringUtils.isEmpty(email)) {
            searchKeys.put(SearchKey.valueOf("EMAIL"), email);
        }

        if (size == 0) {
            List<User> result = service.findAll();
            return ResponseEntity.ok().body(new CommonResponse<List<User>>(result));
        } else {
            Page<User> result = service.findAll(searchKeys, orderby, page, size);
            return ResponseEntity.ok().body(new CommonResponse<Page<User>>(result));
        }
    }
}
