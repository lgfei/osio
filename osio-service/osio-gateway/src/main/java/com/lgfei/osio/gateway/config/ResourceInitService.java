package com.lgfei.osio.gateway.config;

import cn.hutool.core.collection.CollUtil;
import com.lgfei.osio.gateway.infra.RedisConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ResourceInitService {
    private Map<String, List<String>> resourceRolesMap;

    private RedisTemplate<String,Object> redisTemplate;

    public ResourceInitService(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        resourceRolesMap.put("/iam/example/get", CollUtil.toList("ADMIN"));
        resourceRolesMap.put("/iam/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
