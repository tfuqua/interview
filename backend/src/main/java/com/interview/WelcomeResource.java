package com.interview;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeResource {

    @RequestMapping("/api/welcome")
    public String index() {

        return "Welcome to the interview project!";
    }
    
    // Get  /car{id}
    // Get  /car 
    // post /car 
    // update /car 
    // delete /car
    
  
}