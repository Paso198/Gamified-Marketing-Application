package it.polimi.db2.questionnaire.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.ReviewRequest;
import it.polimi.db2.questionnaire.dto.responses.ReviewResponse;
import it.polimi.db2.questionnaire.exceptions.ProductNotFoundException;
import it.polimi.db2.questionnaire.exceptions.UnloggedUserException;
import it.polimi.db2.questionnaire.mappers.ReviewMapper;
import it.polimi.db2.questionnaire.repositories.ReviewRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {
	private final BadWordService badWordService;
	private final ProductService productService;
	private final UserService userService;
	private final ReviewMapper reviewMapper;
	private final ReviewRepository reviewRepository;
	
	@Transactional
	public void addReview(ReviewRequest reviewRequest) {
		if (!badWordService
				.containtsBadWord(List.of(reviewRequest.getReview().split(" "))))
				reviewRepository.save(reviewMapper.toReview(reviewRequest, 
						productService.getProduct(reviewRequest.getProductId()).orElseThrow(()->new ProductNotFoundException("Invalid id", "Product not found")), 
						userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in"))));
		else {
			userService.blockLogged();
		}
	}

	@Transactional(readOnly = true)
	public List<ReviewResponse> getProductReviews(Long id) {
		return reviewMapper.toReviewsResponse(productService.getProduct(id).orElseThrow(()->new ProductNotFoundException("Invalid id", "Product not found")).getReviews());
	}
}
