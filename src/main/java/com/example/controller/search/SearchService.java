package com.example.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Janusz on 06.01.2017.
 */
@Service
public class SearchService {
    private Twitter twitter;

    @Autowired
    SearchService(Twitter twitter){
        this.twitter=twitter;
    }

    public List<Tweet> simpleSearch(String search){
        return twitter.searchOperations().search(search).getTweets();
    }

    public List<Tweet> search(String searchType,List<String> keywords){
        List<SearchParameters> search= keywords.stream()
                .map(taste-> createSearchParam(searchType,taste)).collect(Collectors.toList());
        List<SearchResults> tweets=search.stream()
                .map(taste->twitter.searchOperations().search(taste)).collect(Collectors.toList());
        return tweets.stream()
                .flatMap(searchResults -> searchResults.getTweets().stream()).collect(Collectors.toList());

    }

    private SearchParameters.ResultType getResultType(String searchType){
        for(SearchParameters.ResultType knownType: SearchParameters.ResultType.values()){
            if(knownType.name().equalsIgnoreCase(searchType)){
                return knownType;
            }
        }
        return SearchParameters.ResultType.RECENT;
    }

    private SearchParameters createSearchParam(String searchType, String taste){
        SearchParameters.ResultType resultType=getResultType(searchType);
        SearchParameters searchParameters=new SearchParameters(taste);
        searchParameters.resultType(resultType);
        searchParameters.count(10);
        return searchParameters;
    }
}
