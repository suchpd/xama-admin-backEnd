package com.xama.backend.application.service.dto;

import lombok.Data;

@Data
public class MiniProgramLoginDto {

    private String openId;

    private String sessionKey;
}
