package it.polimi.db2.questionnaire.services;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public void addProduct(ProductRequest ProductRequest) {
		try {
			String extension = FilenameUtils.getExtension(ProductRequest.getImage().getOriginalFilename());
			if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
				Product product = productMapper.toProduct(ProductRequest);
				productRepository.save(product);
			}
			else {
				throw new BadImageException("image", "bad image format");
			}

		} catch (IOException e) {
			throw new BadImageException("image", "bad image content");
		}
		

	}
	
	@Transactional(readOnly = true)
	public Product findProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("invalid id", "product "+id+" not found"));
		return product;
		
	}
	
	@Transactional(readOnly = true)
	public List <ProductResponse> getAllProducts(){
		List <ProductResponse> productsResponse = productMapper.toProductResponsesList(productRepository.findAll().stream());
		return productsResponse;
	}
	
	/*@Transactional
	public void deleteProduct(Long id) {
		productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("invalid id", "product "+id+" not found"));
		productRepository.deleteById(id);
	}*/
	
}
