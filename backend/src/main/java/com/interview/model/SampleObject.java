package com.interview.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SampleObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    public static SampleObject fromDto(SampleObjectDto sampleObjectDto) {
        SampleObject sampleObject = new SampleObject();
        sampleObject.setName(sampleObjectDto.getName());
        sampleObject.setDescription(sampleObjectDto.getDescription());

        return sampleObject;
    }
}
