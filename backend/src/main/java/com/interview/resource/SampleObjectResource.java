package com.interview.resource;

import com.interview.model.SampleObject;
import com.interview.model.SampleObjectDto;
import com.interview.service.SampleObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/sampleObject")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SampleObjectResource {
    private final SampleObjectService sampleObjectService;

    @GetMapping
    public ResponseEntity<Set<SampleObject>> getAll() {
        return ResponseEntity.ok(sampleObjectService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<SampleObject> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(sampleObjectService.getById(id));
    }

    @PostMapping
    public ResponseEntity<SampleObject> create(@RequestBody @Valid SampleObjectDto sampleObjectDto) {
        return ResponseEntity.ok(sampleObjectService.create(sampleObjectDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<SampleObject> modify(
            @PathVariable("id") String id, @RequestBody @Valid SampleObjectDto sampleObjectDto) {
        return ResponseEntity.ok(sampleObjectService.modify(id, sampleObjectDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        sampleObjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
