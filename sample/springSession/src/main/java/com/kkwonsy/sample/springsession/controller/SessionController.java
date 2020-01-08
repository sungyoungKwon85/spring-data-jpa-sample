package com.kkwonsy.sample.springsession.controller;
/*
 *  Copyright (c) 2020 SK planet.
 *  All right reserved.
 *
 *  This software is the confidential and proprietary information of SK Planet.
 *  You shall not disclose such Confidential Information and
 *  shall use it only in accordance with the terms of the license agreement
 *  you entered into with SK planet.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kkwonsy.sample.springsession.service.SessionService;

import lombok.extern.slf4j.Slf4j;

/**
 * Description :
 *
 * @author 권성영/SK Planet (kkwonsy@sk.com)
 * @since 07/01/2020
 */
@Slf4j
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/request")
    public Map getCookie(HttpSession session) {
        return sessionService.haha();
    }
}
