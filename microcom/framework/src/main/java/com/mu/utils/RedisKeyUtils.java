package com.mu.utils;

/**
 * @author MUZUKI
 * @Classname RedisKeyUtils
 * @Description TODO
 * @Date 2023/3/28 23:32
 */

public class RedisKeyUtils {

    /**
     * 分隔符号
     */
    private static final String SPLIT = ":";

    private static final String SEARCH = "search";

    private static final String SEARCH_HISTORY = "search-history";

    private static final String HOT_SEARCH = "hot-search";

    private static final String SEARCH_TIME = "search-time";

    /**
     * 每个用户的个人搜索记录hash
     */
    public static String getSearchHistoryKey(String userId){
        return SEARCH + SPLIT + SEARCH_HISTORY + SPLIT + userId;
    }

    /**
     * 总的热搜zset
     */
    public static String getHotSearchKey(){
        return SEARCH + SPLIT + HOT_SEARCH;
    }


    /**
     * 每个搜索记录的时间戳记录：key-value
     */
    public static String getSearchTimeKey(String searchKey){
        return SEARCH + SPLIT + SEARCH_TIME + SPLIT + searchKey;
    }

}
