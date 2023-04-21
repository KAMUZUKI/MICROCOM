package com.mu.meilisearch.service;

import cn.hutool.core.io.file.FileReader;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.SearchResult;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author : MUZUKI
 * @date : 2023-04-02 18:41
 **/

@Service
public class SearchServiceImpl implements SearchService{
    @Resource
    private Client client;


    @Override
    public SearchResult search(String index ,String q) {
        SearchResult searchResult = null;
        try {
            searchResult = client.index(index).search(q);
        } catch (MeilisearchException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    @Override
    public SearchResult search(String index, SearchRequest searchRequest) {
        SearchResult results = null;
        try {
            results = (SearchResult) client.index(index).search(searchRequest);
        } catch (MeilisearchException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void initLoading(String index,String filePath) {
        FileReader fileReader = new FileReader(new File(filePath));
        String moviesJson = fileReader.readString();
        try {
            client.index(index).addDocuments(moviesJson);
        } catch (MeilisearchException e) {
            throw new RuntimeException(e);
        }
    }
}
