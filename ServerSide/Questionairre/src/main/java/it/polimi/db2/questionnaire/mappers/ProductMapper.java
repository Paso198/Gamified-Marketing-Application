package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.AddProductRequest;
import it.polimi.db2.questionnaire.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="name", expression="java(addProductRequest.getName())" )
	@Mapping(target="photo", expression="java(addProductRequest.getImage().getBytes())" )
	public Product toProduct(AddProductRequest addProductRequest) throws IOException;
}
