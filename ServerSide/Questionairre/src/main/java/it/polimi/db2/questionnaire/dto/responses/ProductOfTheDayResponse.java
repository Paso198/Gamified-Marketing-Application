package it.polimi.db2.questionnaire.dto.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOfTheDayResponse {
	private Long id;
	private String name;
	private byte[] photo;
	private List<ReviewResponse> reviews;
}
