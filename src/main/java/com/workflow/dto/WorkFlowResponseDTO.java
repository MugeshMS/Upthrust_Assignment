package com.workflow.dto;

public class WorkFlowResponseDTO {
	private String ai_response;
	private String api_response;
	private String final_result;
	private String weather_reply;
	private String git_reply;
	private String news_reply;
	public String getAi_response() {
		return ai_response;
	}
	public void setAi_response(String ai_response) {
		this.ai_response = ai_response;
	}
	public String getApi_response() {
		return api_response;
	}
	public void setApi_response(String api_response) {
		this.api_response = api_response;
	}
	public String getFinal_result() {
		return final_result;
	}
	public void setFinal_result(String final_result) {
		this.final_result = final_result;
	}
	public WorkFlowResponseDTO(String ai_response, String api_response, String final_result) {
		super();
		this.ai_response = ai_response;
		this.api_response = api_response;
		this.final_result = final_result;
	}public WorkFlowResponseDTO(String ai_response, String api_response, String final_result, String weather_reply,
			String git_reply, String news_reply) {
		super();
		this.ai_response = ai_response;
		this.api_response = api_response;
		this.final_result = final_result;
		this.weather_reply = weather_reply;
		this.git_reply = git_reply;
		this.news_reply = news_reply;
	}

	public String getWeather_reply() {
		return weather_reply;
	}
	public void setWeather_reply(String weather_reply) {
		this.weather_reply = weather_reply;
	}
	public String getGit_reply() {
		return git_reply;
	}
	public void setGit_reply(String git_reply) {
		this.git_reply = git_reply;
	}
	public String getNews_reply() {
		return news_reply;
	}
	public void setNews_reply(String news_reply) {
		this.news_reply = news_reply;
	}
	WorkFlowResponseDTO(){}

}
