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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * Description :
 *
 * @author 권성영/SK Planet (kkwonsy@sk.com)
 * @since 07/01/2020
 */
@Configuration
@Conditional(HazelcastEnableCondition.class)
public class HazelcastConfig {
    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        MapAttributeConfig attributeConfig = new MapAttributeConfig()
            .setName(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
            .setExtractor(PrincipalNameExtractor.class.getName());
        config.getMapConfig(HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME)
            .addMapAttributeConfig(attributeConfig).addMapIndexConfig(
            new MapIndexConfig(HazelcastIndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
        return Hazelcast.newHazelcastInstance(config);
    }
}
