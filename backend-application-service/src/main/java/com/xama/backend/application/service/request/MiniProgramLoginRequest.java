package com.xama.backend.application.service.request;

import com.xama.backend.application.service.dto.MiniProgramLoginDto;
import com.xama.backend.domain.enumeration.Gender;
import com.xama.backend.infrastructure.mediatr.core.Request;
import lombok.Data;

@Data
public class MiniProgramLoginRequest implements Request<MiniProgramLoginDto> {
    /**
     * 登录Code
     */
    private String code;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 语言
     */
    private String language;

    /**
     * 客户端Ip
     */
    private String clientIp;
}
