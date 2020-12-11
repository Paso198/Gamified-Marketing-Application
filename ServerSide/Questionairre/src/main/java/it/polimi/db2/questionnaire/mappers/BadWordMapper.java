package it.polimi.db2.questionnaire.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.polimi.db2.questionnaire.model.BadWord;

@Mapper(componentModel = "spring")
public interface BadWordMapper {
	
	@Mapping(target="id", ignore=true)
	public BadWord mapToBadWord(String word);
}
