package it.polimi.db2.questionnaire.mappers.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;

import it.polimi.db2.questionnaire.controllers.ProductController;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.mappers.ProductMapper;
import it.polimi.db2.questionnaire.model.Product;

public abstract class ProductMapperDecorator  implements ProductMapper {

	@Autowired
	@Qualifier("delegate")
	private  ProductMapper delegate;

	@Override
	public ProductResponse toProductResponse(Product product) {
		ProductResponse response = delegate.toProductResponse(product);
		response.add(linkTo(methodOn(ProductController.class)
                .getProduct(product.getId()))
                .withSelfRel());
		return response;
		
	}

	@Override
	public List<ProductResponse> toProductResponses(Stream<Product> products) {
		List<ProductResponse> response = delegate.toProductResponses(products);
		return (List<ProductResponse>) CollectionModel.of(response);
		/*return CollectionModel.wrap(response).add(linkTo(methodOn(ProductController.class)
				.getAllProducts())
				.withSelfRel())
				.getContent()
				.stream()
				.map(EntityModel::getContent)
				.collect(Collectors.toList());*/
	}
	
	
	
	

}
