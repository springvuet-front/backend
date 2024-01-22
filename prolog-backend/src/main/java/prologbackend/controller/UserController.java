package prologbackend.controller;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import prologbackend.domain.user.User;
import prologbackend.dto.user.JoinDto;
import prologbackend.service.UserService;

@Lazy
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<User> userJoin(@Valid @RequestBody JoinDto joinDto , BindingResult bindingResult) {
        try {
            //아이디 중복체크
            if (userService.checkEmailDuplicate(joinDto.getEmail())) {
                bindingResult.addError(new FieldError("joinRequest", "email", "중복된 이메일입니다"));
            }

            User newUser = userService.signUp(joinDto);

            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
