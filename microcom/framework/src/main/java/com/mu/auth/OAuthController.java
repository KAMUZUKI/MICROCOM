package com.mu.auth;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mu.entity.DingtalkUser;
import com.mu.utils.SystemUtils;
import com.mu.service.impl.DingtalkServiceImpl;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import me.zhyd.oauth.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author MUZUKI
 * @Classname sd
 * @Description TODO
 * @Date 2023/3/25 20:43
 */

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OAuthController {

    private String token;

    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;

    private final AuthRequestFactory factory;

    @Autowired
    private DingtalkServiceImpl dingtalkService;

    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/login/{type}")
    public SaResult login(@PathVariable String type) throws IOException {
        AuthRequest authRequest = factory.get(type);
        token = AuthStateUtils.createState();
        String authorizeUrl = authRequest.authorize(token);
        return SaResult.ok("成功").setData(authorizeUrl);
    }

    @RequestMapping("/{type}/callback")
    public void login(@PathVariable("type") String source, AuthCallback callback, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        log.info("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = factory.get(source);
        AuthResponse authResponse = authRequest.login(callback);
        String info = JSONUtil.toJsonStr(authResponse.getData());
        Map map = JSON.parseObject(info, Map.class);
        DingtalkUser dingtalkUser = JSON.parseObject(info, DingtalkUser.class);
        dingtalkService.addDingtalkUser(dingtalkUser);
        // 将用户信息转成url形式
        String userinfo = SystemUtils.entityToUrlParam(dingtalkUser, false);
        // 保存用户信息
        stringRedisTemplate.opsForValue().setIfAbsent("dingtalkUser:token:" + token, info, Duration.ofDays(7));
        String url = "http://localhost:8081?token=" + token + "&" + userinfo;
        httpServletResponse.sendRedirect(url);
    }

    @GetMapping("/verify/{accessToken}")
    public SaResult verifyUser(@PathVariable("accessToken") String accessToken) {
        String userInfo = stringRedisTemplate.opsForValue().get("dingtalkUser:token:" + accessToken);
        if (StringUtils.isEmpty(userInfo)) {
            return SaResult.ok("用户未授权或已过期!");
        } else {
            Map<String, Object> map = JSON.parseObject(userInfo, Map.class);
            return SaResult.ok("用户已授权").setData(map);
        }
    }
}
