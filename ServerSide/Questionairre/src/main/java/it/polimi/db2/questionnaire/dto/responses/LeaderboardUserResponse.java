package it.polimi.db2.questionnaire.dto.responses;


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
public class LeaderboardUserResponse {
	private Long id;
	private String username;
	private Integer dailyPoints;
	private Integer totalPoints;
	
}
