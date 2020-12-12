package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.AddProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.mappers.decorators.ProductMapperDecorator;
import it.polimi.db2.questionnaire.model.Product;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="name", expression="java(addProductRequest.getName())" )
	@Mapping(target="photo", expression="java(addProductRequest.getImage().getBytes())" )
	public Product toProduct(AddProductRequest addProductRequest) throws IOException;
	
	@Mapping(target = "photo", expression="java(product.getPhoto())")
	public ProductResponse toProductResponse(Product product);
}
