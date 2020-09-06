package com.interview.dto;

import com.interview.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class PagableUsersDto {

    private int noOfPages;

    private List<User> usersList;
}
