package com.example.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Janusz on 06.01.2017.
 */
@Controller
public class SearchController {
    private SearchService searchService;

    @Autowired
    SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping("search/{searchType}")
    public ModelAndView search(@PathVariable String searchType,
                               @MatrixVariable List<String> keywords) {
        List<LightTweet> tweets = searchService.search(searchType, keywords);
        ModelAndView modelAndView = new ModelAndView("search/resultPage");
        modelAndView.addObject("tweets", tweets);
        modelAndView.addObject("search",String.join(",",keywords));
        return modelAndView;
    }

    @RequestMapping("/simpleSearch")
    public String simpleSearch(){
        return "search/searchPage";
    }

    @RequestMapping("/result")
    public String result(@RequestParam(defaultValue = "TajnikiSpringMVC4") String search, Model model) {
        List<Tweet> tweets = searchService.simpleSearch(search);
        model.addAttribute("tweets",tweets);
        model.addAttribute("search", search);
        return "search/resultPage";
    }
}

