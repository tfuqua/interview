package com.interview.entity;

import com.interview.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book extends BaseEntity {
    private String name;
    private String authorName;
    private String authorSurname;
    private String imageUrl;
    private LocalDate publishDate;
}
