package com.example.controller.search.api;

import com.example.controller.search.LightTweet;
import com.example.controller.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Janusz on 10.01.2017.
 */

@RestController
@RequestMapping("/api/search")
public class SearchApiController {
    private SearchService searchService;

    @Autowired
    public SearchApiController(SearchService searchService){
        this.searchService=searchService;
    }

    @RequestMapping(value = "/{searchType}",method = RequestMethod.GET)
    public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        return searchService.search(searchType,keywords);
    }
}
