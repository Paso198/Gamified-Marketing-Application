package it.polimi.db2.questionnaire.services;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.AddProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.exceptions.BadImageException;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.mappers.ProductMapper;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.repositories.ProductRepository;
import it.polimi.db2.questionnaire.repositories.QuestionnaireRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final QuestionnaireRepository questionnaireRepository;
	private final ProductMapper productMapper;

	public void addProduct(AddProductRequest addProductRequest) {
		try {
			String extension = FilenameUtils.getExtension(addProductRequest.getImage().getOriginalFilename());
			if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {			//TODO: can we do better
				Product product = productMapper.toProduct(addProductRequest);

				productRepository.save(product);
			}
			else {
				throw new BadImageException("image", "bad image format");
			}

		} catch (IOException e) {
			throw new BadImageException("image", "bad image content");
		}
		

	}
	
	
	public ProductResponse getProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("invalid id", "product "+id+" not found"));
		return productMapper.toProductResponse(product);
		
	}
	
	public CollectionModel <ProductResponse> getAllProducts()
	{
		/*List <ProductResponse> response = productRepository.findAll()
				.stream()
				.map(productMapper::toProductResponse)
				.collect(Collectors.toList());*/
		
		CollectionModel <ProductResponse> response = productMapper.toProductResponsesCollectionModel(productRepository.findAll().stream());
		return response;
	}
}
