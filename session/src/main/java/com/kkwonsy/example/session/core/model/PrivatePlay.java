/*
 * Copyright (c) 2019 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.kkwonsy.example.session.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PrivatePlay {

    private String deviceUniqueId;
    private String userKey;
    private String deviceKey;
}
