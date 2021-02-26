package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.CollectionModel;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.model.Product;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
	
	@Mapping(target = "questionnaires", ignore = true)
	@Mapping(target="id", ignore=true)
	@Mapping(target="name", expression="java(productRequest.getName())" )
	@Mapping(target="photo", expression="java(productRequest.getImage().getBytes())" )
	public Product toProduct(ProductRequest productRequest) throws IOException;
	
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

	 ProductMapper INSTANCE= Mappers.getMapper(ProductMapper.class);
}
