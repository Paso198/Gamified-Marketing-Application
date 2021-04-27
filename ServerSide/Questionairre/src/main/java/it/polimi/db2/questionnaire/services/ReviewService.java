package it.polimi.db2.questionnaire.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.ReviewRequest;
import it.polimi.db2.questionnaire.exceptions.BadWordException;
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
	
	@Transactional(noRollbackFor=BadWordException.class)
	public void addReview(ReviewRequest reviewRequest) {
		if (!badWordService
				.containtsBadWord(List.of(reviewRequest.getReview().split("\\W+"))))
				reviewRepository.save(reviewMapper.toReview(reviewRequest, 
						productService.getProduct(reviewRequest.getProductId()).orElseThrow(()->new ProductNotFoundException("Invalid id", "Product not found")), 
						userService.getLoggedUser().orElseThrow(() -> new UnloggedUserException("Not user currently logged in"))));
		else {
			userService.blockLogged();
			throw new BadWordException("Review not accepted", "Bad words are not allowed");
		}
	}
}
