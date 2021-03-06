package it.polimi.db2.questionnaire.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Void> addProduct(@Valid @ModelAttribute ProductRequest productRequest) {
		productService.addProduct(productRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/admin/products/")
	public List <ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}
	
}
