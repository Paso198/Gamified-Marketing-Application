package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.dto.requests.ReviewRequest;
import it.polimi.db2.questionnaire.dto.responses.ReviewResponse;
import it.polimi.db2.questionnaire.model.Product;
import it.polimi.db2.questionnaire.model.Review;
import it.polimi.db2.questionnaire.model.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReviewMapper {
	
	@Mapping(target="username", source="user.username")
	@Mapping(target="review", source="review")
	public ReviewResponse toReviewResponse(Review review);
	
	@Mapping(target="id", ignore=true)
	@Mapping(target="user", source="user")
	@Mapping(target="review", source="reviewRequest.review")
	@Mapping(target="product", source="product")
	public Review toReview(ReviewRequest reviewRequest, Product product, User user);

	public List<ReviewResponse> toReviewsResponse(List<Review> reviews);

}
