package com.example.controller.user.api;

import com.example.controller.Error.EntityNotFoundException;
import com.example.controller.user.User;
import com.example.controller.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Janusz on 12.01.2017.
 */
@RestController
@RequestMapping("/api")
public class UserApiController {
    private UserRepository userRepository;

    @Autowired
    public UserApiController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @RequestMapping(value = "/users", method= RequestMethod.GET)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user){
        HttpStatus status=HttpStatus.OK;
        if(!userRepository.isExist(user.getEmail())){
            status=HttpStatus.CREATED;
        }
        User saved=userRepository.save(user);
        return new ResponseEntity<>(saved,status);
    }

    @RequestMapping(value = "/users/{email}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) throws EntityNotFoundException {
        if(!userRepository.isExist(email)){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        User saved = userRepository.save(email,user);
        return new ResponseEntity<User>(saved,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String email) throws EntityNotFoundException {
        if(!userRepository.isExist(email)){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(email);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

}
