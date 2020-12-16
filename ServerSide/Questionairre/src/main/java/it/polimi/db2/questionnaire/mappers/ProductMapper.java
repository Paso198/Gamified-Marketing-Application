package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.DecoratedWith;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.hateoas.CollectionModel;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.mappers.decorators.ProductMapperDecorator;
import it.polimi.db2.questionnaire.model.Product;

@Mapper(componentModel = "spring")
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="name", expression="java(addProductRequest.getName())" )
	@Mapping(target="photo", expression="java(addProductRequest.getImage().getBytes())" )
	public Product toProduct(ProductRequest addProductRequest) throws IOException;
	
	@Named("decorated")
	@Mapping(target = "photo", expression="java(product.getPhoto())")
	public ProductResponse toProductResponse(Product product);
	
	@IterableMapping(qualifiedByName= "decorated")
	public List <ProductResponse> toProductResponsesList(Stream<Product> products);
	
	default CollectionModel <ProductResponse> toProductResponsesCollectionModel(Stream<Product> products){
		return CollectionModel.of(toProductResponsesList(products));
	}
	
	@Mapping(target = "photo", expression="java(product.getPhoto())")
	public ProductResponse toProductInQuestionnaireResponse(Product product);
}
