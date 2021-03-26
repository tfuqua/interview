package com.interview.assembler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.interview.model.ProductDto;
import com.interview.resource.ProductResource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {

    public EntityModel<ProductDto> toModel(ProductDto product) {

        return EntityModel.of(product,
                linkTo(methodOn(ProductResource.class).getById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductResource.class).getAll()).withRel("products"));
    }

    public CollectionModel<EntityModel<ProductDto>> toCollectionModel(List<ProductDto> products) {
        return CollectionModel.of(products.stream().map(this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(ProductResource.class).getAll()).withSelfRel());
    }
}
