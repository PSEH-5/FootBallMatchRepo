package com.example.football.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.football.dto.CountryDto;
import com.example.football.dto.FootBallDto;
import com.example.football.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author AyashKantB
 */
@RequestMapping("/football")
@RestController
public class FootBallController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;

	@RequestMapping(Constants.GET_DETAILS_BY_COUNTRYNAME_LEAGUENAME_TEAMNAME)
	public FootBallDto getFootBallDetails(@PathVariable String countryName,@PathVariable String leagueName,@PathVariable String teamName) {
		final String countryApiUri =  "https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
		final String statndingsApiUri =  "https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
		String countryJsonString=restTemplate.getForObject(countryApiUri, String.class);
		CountryDto[] countryList = null;
		FootBallDto resultFootBallDto=null;
		try{
			countryList=mapper.readValue(countryJsonString, CountryDto[].class);
			Map<String,CountryDto> countryMap=new HashMap<>();
			for(CountryDto countryDto:countryList)
				countryMap.put(countryDto.getCountryName(),countryDto);
			CountryDto resultCountryDto=countryMap.get(countryName);
			String standingsJson=restTemplate.getForObject(statndingsApiUri, String.class);
			FootBallDto[] foBallDtoList=mapper.readValue(standingsJson, FootBallDto[].class);
			Map<String,FootBallDto> footBallDtoMap=new HashMap<>();
			for(FootBallDto footBallDto:foBallDtoList)
				footBallDtoMap.put(footBallDto.getLeagueName(), footBallDto);
			resultFootBallDto=footBallDtoMap.get(leagueName);
			resultFootBallDto.setCountryId(resultCountryDto.getCountryId());
			
		}catch(Exception ex) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resultFootBallDto;
		
	}
	
}
