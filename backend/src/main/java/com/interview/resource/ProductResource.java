package com.interview.resource;

import java.util.List;
import javax.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.interview.assembler.ProductModelAssembler;
import com.interview.model.ProductDto;
import com.interview.service.ProductService;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductResource {

    private final ProductService service;
    private final ProductModelAssembler assembler;

    @GetMapping()
    public CollectionModel<EntityModel<ProductDto>> getAll() {
        List<ProductDto> products = service.get();
        return assembler.toCollectionModel(products);
    }

    @GetMapping("{id}")
    public EntityModel<ProductDto> getById(@PathVariable Long id) {
        ProductDto productDto = service.get(id);

        return assembler.toModel(productDto);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto product) {
        EntityModel<ProductDto> entityModel = assembler.toModel(service.create(product));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    public EntityModel<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        dto.setId(id);

        return assembler.toModel(service.update(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
