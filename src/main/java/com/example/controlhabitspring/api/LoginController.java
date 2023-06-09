package com.example.controlhabitspring.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.controlhabitspring.domain.User;
import com.example.controlhabitspring.service.UserService;
import lombok.extern.slf4j.Slf4j;

import com.example.controlhabitspring.model.BasicResponse;
import com.example.controlhabitspring.model.ErrorResponse;
import com.example.controlhabitspring.model.CommonResponse;


@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserService service;

    // @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @GetMapping("")
    public ResponseEntity<? extends BasicResponse> login(@RequestParam(value = "email") String email, @RequestParam(value = "passwd") String passwd) {
        Optional<User> opt = service.findByEmail(email);
        if (!opt.isPresent()) {
            log.info("not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("사용자를 찾을수가 없습니다"));
        }

        User user = opt.get();

        log.info("passwd = " + passwd);
        log.info("user.passwd = " + user.getUserPw());

        if (user.getUserPw().equals(passwd)) {
            return ResponseEntity.ok().body(new CommonResponse<User>(user));
        } else {
            log.info("wrong passwd");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("비밀번호가 맞지 않습니다."));
        }
    }

    // @ApiOperation("로그아웃")
    // @RequestMapping(value = "logout", method = RequestMethod.GET)
    @GetMapping("logout")
    public ResponseEntity<? extends BasicResponse> logout() {
        return ResponseEntity.noContent().build();
    }
}
