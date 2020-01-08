package com.interview.service;

import com.interview.exception.SampleObjectNotFoundException;
import com.interview.model.SampleObject;
import com.interview.model.SampleObjectDto;
import com.interview.repository.SampleObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SampleObjectService {
    private final SampleObjectRepository sampleObjectRepository;

    public Set<SampleObject> getAll() {
        return sampleObjectRepository.findAll();
    }

    public SampleObject getById(String id) {
        return sampleObjectRepository.findById(parseId(id)).orElseThrow(() ->
            new SampleObjectNotFoundException(String.format("Sample object with id: %s cannot be found", id))
        );
    }

    public SampleObject create(SampleObjectDto sampleObjectDto) {
        SampleObject sampleObject = SampleObject.fromDto(sampleObjectDto);

        return sampleObjectRepository.save(sampleObject);
    }

    public SampleObject modify(String id, SampleObjectDto sampleObjectDto) {
        SampleObject sampleObject = sampleObjectRepository.findById(parseId(id)).orElseThrow(() ->
                new SampleObjectNotFoundException(String.format("Sample object with id: %s cannot be found", id))
        );

        sampleObject.setName(Objects.nonNull(sampleObjectDto.getName()) ?
                sampleObjectDto.getName() : sampleObject.getName());
        sampleObject.setDescription(Objects.nonNull(sampleObjectDto.getDescription()) ?
                sampleObjectDto.getDescription() : sampleObject.getDescription());

        return sampleObjectRepository.save(sampleObject);
    }

    public void delete(String id) {
        sampleObjectRepository.deleteById(parseId(id));
    }

    private long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new SampleObjectNotFoundException(String.format("Sample object with id: %s cannot be found", id));
        }
    }
}
