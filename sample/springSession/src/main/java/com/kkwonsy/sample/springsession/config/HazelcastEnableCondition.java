package com.kkwonsy.sample.springsession.config;
/*
 *  Copyright (c) 2020 SK planet.
 *  All right reserved.
 *
 *  This software is the confidential and proprietary information of SK Planet.
 *  You shall not disclose such Confidential Information and
 *  shall use it only in accordance with the terms of the license agreement
 *  you entered into with SK planet.
 */

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Description :
 *
 * @author 권성영/SK Planet (kkwonsy@sk.com)
 * @since 07/01/2020
 */
public class HazelcastEnableCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        return null != env && ("hazelcast".equals(env.getProperty("spring.session.store-type")));
    }
}
