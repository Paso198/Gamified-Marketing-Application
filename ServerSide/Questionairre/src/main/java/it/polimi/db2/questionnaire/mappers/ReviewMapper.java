package it.polimi.db2.questionnaire.mappers;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import it.polimi.db2.questionnaire.dto.responses.ReviewResponse;
import it.polimi.db2.questionnaire.model.Review;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReviewMapper {
	//TODO ....
	public ReviewResponse toReviewResponse(Review review);

	public List<ReviewResponse> toReviewsResponse(List<Review> reviews);

}
