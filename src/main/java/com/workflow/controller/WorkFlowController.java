package com.workflow.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workflow.dto.WorkFlowRequestDTO;
import com.workflow.dto.WorkFlowResponseDTO;
import com.workflow.service.AIService;
import com.workflow.service.GithubService;
import com.workflow.service.NEWSService;
import com.workflow.service.WeatherService;

@Controller
public class WorkFlowController {
	private AIService ai;
	private WeatherService ws;
	private GithubService gs;
	private NEWSService ns;
		
	public WorkFlowController(AIService ai, WeatherService ws,GithubService gs,NEWSService ns) {
		super();
		this.ai = ai;
		this.ws = ws;
		this.gs=gs;
		this.ns = ns;
	}
	@GetMapping("/")
	public String index() {
		return	"index";
	}
	
	@PostMapping("/run-workflow")
	@ResponseBody
	public WorkFlowResponseDTO getPromt(@RequestBody WorkFlowRequestDTO req) {
	    String action = req.getAction();
	    String prompt = req.getPrompt();

	    String ai_reply = "";
	    String weather_reply = "";
	    String git_reply = "";
	    String news_reply = "";
	    String api_reply = "";

	    switch (action.toLowerCase()) {
	        case "weather":
	            weather_reply = ws.getWeatherTweet("india");
	            api_reply = weather_reply + " #Weather";
	            ai_reply = ai.generateText("Write a very short, one-line cheerful comment about this weather: " 
	                        + prompt + ". Do not repeat the weather details. No emojis, no hashtags.");
	            break;

	        case "github":
	            git_reply = gs.getTrendingRepos(1);
	            api_reply = git_reply + " #Repos";
	            ai_reply = ai.generateText("Write a very short, one-line note about this trending GitHub repo: " 
	                        + api_reply + ". Do not repeat the repo name or stars. No emojis, no hashtags.");
	            break;

	        case "news":
	            news_reply = ns.getTrendingNews(1);
	            api_reply = news_reply+" #news";
	            ai_reply = ai.generateText("Write a very short, one-line reaction to this news: " 
	                        + api_reply + ". Do not repeat the headline. No emojis, no hashtags.");
	            break;

	        case "all":
	        	ai_reply = ai.generateText("Write a short one-liner about: " + prompt);
	            weather_reply = ws.getWeatherTweet("india");
	            git_reply = gs.getTrendingRepos(1);
	            news_reply = ns.getTrendingNews(1);
	            
	            break;

	    }

	    // final result can be just the AI reply + any selected API reply
	    String final_result = ai_reply + api_reply;
	    
	    return new WorkFlowResponseDTO(ai_reply, api_reply, final_result, weather_reply, git_reply, news_reply);
	}

}
