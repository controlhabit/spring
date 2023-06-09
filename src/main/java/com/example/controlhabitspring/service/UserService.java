package com.example.controlhabitspring.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import com.example.controlhabitspring.domain.User;
import com.example.controlhabitspring.domain.UserRepository;
import com.example.controlhabitspring.domain.UserSpecs;
import com.example.controlhabitspring.domain.UserSpecs.SearchKey;

@Slf4j
@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Transactional
    public User insert(User item) {
        Optional<User> opt = repository.findByUserId(item.getUserId());

        if (opt.isPresent()) {
            return null;
        }

        /*
        String passwd = sha256(item.getUserPw());
        item.setUserPw(passwd);
        */

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(9);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        item.setUserDate(timestamp);

        return repository.save(item);
    }

    @Transactional
    public User update(User item) {
        Optional<User> opt = repository.findByUserId(item.getUserId());

        if (!opt.isPresent()) {
            return null;
        }

        User old = opt.get();

        String passwd = item.getUserPw();
        if (StringUtils.isEmpty(passwd)) {
            item.setUserPw(old.getUserPw());
        } else {
            //item.setUserPw(sha256(passwd));
            item.setUserPw(passwd);
        }

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(9);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        item.setUserDate(timestamp);

        return repository.save(item);
    }

     @Transactional
    public void delete(User item) {
        repository.delete(item);
    }

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
