package com.example.maruf.a2svc.controllers;

import com.example.maruf.a2svc.models.User;
import com.example.maruf.a2svc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    //    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    //    @PostMapping
//    public  void  registerNewUser(@RequestBody User user){
//        userService.addUser(user);
//    }
    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateUserTitle(@PathVariable("userId") Long userId, @RequestBody User user){
        System.out.println(userId);
        User updatedUser = userService.updateTitle(userId, user);
        return ResponseEntity.ok().body(updatedUser);
    }

}