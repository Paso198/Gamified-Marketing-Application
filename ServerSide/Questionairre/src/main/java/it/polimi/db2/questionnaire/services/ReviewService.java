package it.polimi.db2.questionnaire.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.polimi.db2.questionnaire.dto.requests.ReviewRequest;
import it.polimi.db2.questionnaire.repositories.ReviewRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {
	private final BadWordService badWordService;
	private final UserService userService;
	private final ReviewRepository reviewRepository;
	
	@Transactional
	public void addReview(ReviewRequest reviewRequest) {
		if (!badWordService
				.containtsBadWord(List.of(reviewRequest.getReview().split(" "))))
				reviewRepository.save(null); //TODO map to model
		else {
			userService.blockLogged();
		}
	}
}
