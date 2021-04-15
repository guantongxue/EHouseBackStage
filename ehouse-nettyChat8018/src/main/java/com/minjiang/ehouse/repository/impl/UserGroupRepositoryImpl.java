package com.minjiang.ehouse.repository.impl;


import com.minjiang.ehouse.repository.UserGroupRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dominick Li
 * @CreateTime 2020/5/8 21:54
 * @description
 **/
@Component
public class UserGroupRepositoryImpl implements UserGroupRepository {
    /**
     * 组装假数据,真实环境应该重数据库获取
     */
    HashMap<Long, List<Long>> userGroup = new HashMap<Long, List<Long>>(4);

    {
        List<Long> list = Arrays.asList(Long.valueOf(1), Long.valueOf(2));
        userGroup.put(Long.valueOf(1), list);
        userGroup.put(Long.valueOf(2), list);
        userGroup.put(Long.valueOf(3), list);
        userGroup.put(Long.valueOf(4), list);
    }

    @Override
    public List<Long> findGroupIdByUserId(Long userId) {
        return this.userGroup.get(userId);
    }
}
