package com.mu.meilisearch.service;

import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.model.SearchResult;

/**
 * @author : MUZUKI
 * @date : 2023-04-02 18:41
 **/

public interface SearchService {
    /**
     * 搜索
     * @param index 索引
     * @param q 搜索关键字
     * @return 搜索结果
     */
    SearchResult search(String index , String q);

    /**
     * 搜索
     * @param index 索引
     * @param searchRequest 搜索请求
     * @return 搜索结果
     */
    SearchResult search(String index, SearchRequest searchRequest);

    /**
     * 初始化加载
     * @param index 索引
     * @param filePath 文件路径
     */
    void initLoading(String index,String filePath);
}
