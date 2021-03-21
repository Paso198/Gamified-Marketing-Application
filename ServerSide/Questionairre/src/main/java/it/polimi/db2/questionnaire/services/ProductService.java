package it.polimi.db2.questionnaire.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductOfTheDayResponse;
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
	public void addProduct(ProductRequest productRequest) {
		try {
			String extension = FilenameUtils.getExtension(productRequest.getImage().getOriginalFilename());
			if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
				Product product = productMapper.toProduct(productRequest);
				productRepository.save(product);
			}
			else {
				throw new BadImageException("Image", "Bad image format");
			}

		} catch (IOException e) {
			throw new BadImageException("Image", "Bad image content");
		}
	}
	
	@Transactional(readOnly = true)
	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);	
	}
	
	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProducts(){
		List<ProductResponse> productsResponse = productMapper.toProductsResponse(productRepository.findAll());
		return productsResponse;
	}
	
	@Transactional(readOnly = true)
	public ProductOfTheDayResponse getProductOfTheDay() {
		return productMapper.toProductOfTheDayResponse(productRepository.findProductOfTheDay()
				.orElseThrow(()->new ProductNotFoundException("Product of the day not available yet", "No Product found for current date")));	
	}
	
}
