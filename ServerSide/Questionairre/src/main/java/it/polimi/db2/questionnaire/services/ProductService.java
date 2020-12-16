package it.polimi.db2.questionnaire.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.exceptions.BadImageException;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.mappers.ProductMapper;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.repositories.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	
	@Transactional
	public void addProduct(ProductRequest addProductRequest) {
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
	
	@Transactional
	public ProductResponse getProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("invalid id", "product "+id+" not found"));
		return productMapper.toProductResponse(product);
		
	}
	
	@Transactional
	public CollectionModel <ProductResponse> getAllProducts(){
		CollectionModel <ProductResponse> response = productMapper.toProductResponsesCollectionModel(productRepository.findAll().stream());
		return response;
	}
	
	@Transactional
	public void deleteProduct(Long id) {
		productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("invalid id", "product "+id+" not found"));
		productRepository.deleteById(id);
	}
	
	@Transactional
	public void updateProduct(Long id, ProductRequest productRequest) {
		
	}
}
