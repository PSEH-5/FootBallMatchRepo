package com.sapient.hiring.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;


@Path("/league")
public class FootBall {
	
	
	
	public String getTeams(int leagueId) {
		String URL = "https://apiv2.apifootball.com/?action=get_teams&league_id="+leagueId+"&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
		JSONObject obj = new JSONObject();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URL);
		Invocation.Builder invocation = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		String result = response.getEntity().toString();
		System.out.print(result);
		JSONArray jsonArray = new JSONArray(result);
		JSONObject teams = new JSONObject().put("teams", jsonArray);
		return teams.toString();
	}
	
	public String getLeagues(int countryId, String leaguename) {
		String URL = "https://apiv2.apifootball.com/?action=get_leagues&country_id="+countryId+"&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
		JSONObject obj = new JSONObject();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URL);
		Invocation.Builder invocation = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		String result = response.getEntity().toString();
		System.out.print(result);
		JSONArray jsonArray = new JSONArray(result);
		for(int i=0;i<jsonArray.length(); i++) {
			JSONObject tempObj = jsonArray.getJSONObject(i);
			if(tempObj.get("league_name") == leaguename) {
				return tempObj.toString();
			}
		}
		return "No Leagues";
	}
	
	

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCountries(@QueryParam("country") String CountryName,
			@QueryParam(value = "league") String LeagueName,
			@QueryParam(value="team") String TeamName) {
		
		int countryId = 0;
		JSONObject obj = new JSONObject();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978");
		Invocation.Builder invocation = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocation.get();
		String result = response.getEntity().toString();
		System.out.print(result);
		JSONArray jsonArray = new JSONArray(result);
		for(int i=0;i<jsonArray.length(); i++) {
			 obj = jsonArray.getJSONObject(i);
			if(obj.get("country_name") == CountryName) {
				countryId = Integer.parseInt(obj.get("country_id").toString());
				break;
			}
		}
		
		//function to get leagues 
		String leagueResponse = getLeagues(countryId, LeagueName);
		JSONObject jsonLeagueResponse = new JSONObject(leagueResponse);
		
		
		//get teams
		String teamResponse = getTeams(jsonLeagueResponse.getInt("league_id"));
		
		JSONObject finalResponse = new JSONObject().put("country", jsonLeagueResponse)
									.put("Team", teamResponse);
		
		return finalResponse.toString();
	
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTest() {
		
		
		System.out.print("testing");
		return "Testing";
		
	}
	
	
}
