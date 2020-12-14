package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.ProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.services.ProductService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping(value = "/admin/products", consumes = { "multipart/form-data" })
	public ResponseEntity<Void> addProduct(@Valid @ModelAttribute ProductRequest product) {
		productService.addProduct(product);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/admin/products/{id}")
	public ProductResponse getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}
	
	@GetMapping("/admin/products/")
	public CollectionModel <ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@DeleteMapping("/admin/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/admin/products/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable Long id, ProductRequest product) {
		productService.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
