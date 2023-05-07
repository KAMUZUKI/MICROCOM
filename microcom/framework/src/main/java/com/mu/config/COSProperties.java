package com.mu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : MUZUKI
 * @date : 2023-03-22 15:33
 **/

@Data
@ConfigurationProperties(prefix = "qcloud")
public class COSProperties {
    /**
     * 初始化用户身份信息 前往密钥管理查看
     */
    private   String secretId;
    /**
     * 初始化用户身份信息 前往密钥管理查看
     */
    private String secretKey;
    /**
     * 指定要上传到的存储桶
     */
    private String bucketName;
    /**
     * 指定要上传的地区名称 去控制台查询
     */
    private String regionName;
}