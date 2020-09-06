package com.interview.controller;

import com.interview.dto.UserDto;
import com.interview.entity.User;
import com.interview.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.logging.Level;

@RestController
@Log
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody UserDto user, @PathVariable(value = "id") @Min(1) Long id) {
        log.log(Level.INFO, "Updating User " + user.getEmailId());
        return ResponseEntity.ok(userService.updateUser(user, id));
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserDto user) {
        log.log(Level.INFO, "Adding User " + user.getEmailId());
        User responseUser = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/user").path("/{id}")
                .buildAndExpand(responseUser.getId()).toUri();
        return ResponseEntity.created(location).body(responseUser);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsersList( @RequestParam("pageNo") @Min(1) Integer pageNo,
                                                   @RequestParam("pageSize") @Min(1) Integer pageSize){


        return ResponseEntity.ok(userService.getUserList(pageNo,pageSize));
    }



    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(@PathVariable(value = "id") @Min(1) Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

//    @RequestMapping(path = "/users", method = RequestMethod.GET)
//    public ResponseEntity<?> getUserList() {
//        return ResponseEntity.ok(userService.getUserList());
//    }
//

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") @Min(1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Successfully Deleted");
    }

}
