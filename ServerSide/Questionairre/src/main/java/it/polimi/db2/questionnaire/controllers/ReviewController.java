package it.polimi.db2.questionnaire.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.polimi.db2.questionnaire.dto.requests.ReviewRequest;
import it.polimi.db2.questionnaire.services.ReviewService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	
	@PostMapping(value = "/public/products/reviews")
	public ResponseEntity<Void> addReview(@Valid @RequestBody ReviewRequest reviewRequest) {
		reviewService.addReview(reviewRequest);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
