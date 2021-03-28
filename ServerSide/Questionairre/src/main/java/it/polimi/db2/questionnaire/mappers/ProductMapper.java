package it.polimi.db2.questionnaire.mappers;

import java.io.IOException;
import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductOfTheDayResponse;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.model.Product;

@Mapper(componentModel = "spring", uses = ReviewMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {
	
	@Mapping(target = "reviews", ignore = true)
	@Mapping(target = "questionnaires", ignore = true)
	@Mapping(target = "id", ignore=true)
	@Mapping(target = "name", source="name" )
	@Mapping(target = "photo", source="image.bytes" )
	public Product toProduct(ProductRequest productRequest) throws IOException;
	
	@Mapping(target = "photo", source="photo")
	@Mapping(target = "id", source="id")
	@Mapping(target = "name", source="name")
	public ProductResponse toProductResponse(Product product);
	
	public List<ProductResponse> toProductsResponse(List<Product> products);

	@Mapping(target = "photo", source="photo")
	@Mapping(target = "id", source="id")
	@Mapping(target = "name", source="name")
	@Mapping(target = "reviews", source="reviews")
	public ProductOfTheDayResponse toProductOfTheDayResponse(Product product);
}
