package com.kkwonsy.example.session.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * com.skp.air.core.play.model
 *
 * @author texhxcho@sk.com
 * @since 2019-04-25
 */
@Builder
@Getter
@ToString
public class Session {

    private boolean isPlayBuilderRequest;
    private String id;
    private String isNew;
}
