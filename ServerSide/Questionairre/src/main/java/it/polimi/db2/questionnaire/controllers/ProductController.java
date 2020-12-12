package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.AddProductRequest;
import it.polimi.db2.questionnaire.dto.responses.ProductResponse;
import it.polimi.db2.questionnaire.services.ProductService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping(value = "/admin/products", consumes = { "multipart/form-data" })
	public void addProduct(@Valid @ModelAttribute AddProductRequest product) {
		productService.addProduct(product);
	}

	@GetMapping("/admin/products/{id}")
	public ProductResponse getProduct(@PathVariable Long id) {
		return productService.getProduct(id);
	}
}
