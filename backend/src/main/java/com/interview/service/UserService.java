package com.interview.service;

import com.interview.dto.PagableUsersDto;
import com.interview.dto.UserDto;
import com.interview.entity.User;
import com.interview.exceptions.exceptions.CustomException;
import com.interview.exceptions.exceptions.NotFoundException;
import com.interview.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDto userDto){

        if(userRepository.existsByEmailId(userDto.getEmailId())) {
            throw new CustomException("Email Id already Exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmailId(userDto.getEmailId());
        user.setWebsite(userDto.getWebsite());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setFirstName(userDto.getFirstName());

        return userRepository.save(user);
    }
    public User updateUser(UserDto userDto, Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

//        if(userDto.getEmailId() != null) user.setEmailId(userDto.getEmailId());
        if(userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
        if(userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if(userDto.getPhoneNumber() != null) user.setPhoneNumber(userDto.getPhoneNumber());
        if(userDto.getWebsite() != null) user.setWebsite(userDto.getWebsite());

        return userRepository.save(user);
    }


    public User getUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id '" + id + "' Not Found"));
        return user;
    }

    public User getUser(String emailId){
        User user =  userRepository.findByEmailId(emailId).
                orElseThrow(() -> new NotFoundException("EmailId "+ emailId+" Not Found"));
        return user;
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public PagableUsersDto getUserList(int pageNum, int pageSize){

        Pageable pageable = PageRequest.of(--pageNum,pageSize);

        Page<User> pagedResult = null;

        pagedResult = userRepository.getUsersBy(pageable);

        PagableUsersDto pagableUsersDto = new PagableUsersDto();
        pagableUsersDto.setNoOfPages(pagedResult.getTotalPages());

        if(pagedResult.hasContent()){
            pagableUsersDto.setUsersList(pagedResult.getContent());
        }else{
            pagableUsersDto.setUsersList(new ArrayList<>());
        }

        return pagableUsersDto;
    }

    public void deleteUser(Long id){
        try {
            userRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new CustomException("User Not Found",e,HttpStatus.NO_CONTENT);
        }
    }

}
