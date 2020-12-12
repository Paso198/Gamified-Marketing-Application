package it.polimi.db2.questionnaire.mappers.decorators;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import org.springframework.beans.factory.annotation.Qualifier;

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
//		try {
//			Link link= WebMvcLinkBuilder.linkTo((ProductController.class).getMethod("getProduct", Long.class)).withSelfRel();
//			return response.add(link);
//		} catch (NoSuchMethodException | SecurityException e) {
//			throw new ProductNotFoundException("invalid product", "invalid "+product.getId()+" product");
//		}
		response.add(linkTo(methodOn(ProductController.class)
                .getProduct(product.getId()))
                .withSelfRel());
		return response;
		
	}
	
	

}
