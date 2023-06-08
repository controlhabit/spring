package com.example.controlhabitspring.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import com.example.controlhabitspring.domain.User;
import com.example.controlhabitspring.domain.UserRepository;
import com.example.controlhabitspring.domain.UserSpecs;
import com.example.controlhabitspring.domain.UserSpecs.SearchKey;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

     public Optional<User> findById(String userId) {
        return repository.findByUserId(userId);
    }

    public Page<User> findAll(Map<SearchKey, Object> searchKeys, String order, int page, int size) {
        Sort sort = null;
        boolean desc = false;

        if (StringUtils.isEmpty(order)) {
            order = "userId";
        } else {
            if (StringUtils.right(order, 4).equals("Desc")) {
                order = StringUtils.left(order, order.length() - 4);
                desc = true;
            }
        }

        sort = Sort.by(order);

        if (desc) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return searchKeys.isEmpty()
            ? repository.findAll(pageable)
            : repository.findAll(UserSpecs.searchWith(searchKeys), pageable);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
    
}
