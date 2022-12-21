package com.xama.backend.application.service.service;

import com.xama.backend.application.service.dto.MiniProgramLoginDto;
import com.xama.backend.domain.dto.MiniProgramUserDto;
import com.xama.backend.domain.entity.MiniProgramUser;

public interface MiniProgramUserService {

    /**
     * 注册
     * @param miniProgramUserDto    注册信息
     * @return  注册用户
     */
    MiniProgramUser register(MiniProgramUserDto miniProgramUserDto);

    /**
     * 登录
     * @param loginData    登录信息
     * @param lastLoginIp   最后一次登录Ip
     */
    void login(MiniProgramLoginDto loginData, String lastLoginIp);
}
