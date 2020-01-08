package com.kkwonsy.sample.springsession.service;
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
import org.springframework.stereotype.Service;

/**
 * Description :
 *
 * @author 권성영/SK Planet (kkwonsy@sk.com)
 * @since 07/01/2020
 */
@Service
public class SessionService {

    @Autowired
    HttpSession httpSession;

    public Map haha() {
        UUID uid = Optional.ofNullable(UUID.class.cast(httpSession.getAttribute("uid")))
            .orElse(UUID.randomUUID());
        httpSession.setAttribute("uid", uid);
        Map m = new HashMap<>();
        m.put("uuid", uid.toString());
        return m;
    }
}
