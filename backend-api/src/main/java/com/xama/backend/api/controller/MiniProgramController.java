package com.xama.backend.api.controller;

import com.xama.backend.application.service.dto.MiniProgramLoginDto;
import com.xama.backend.application.service.request.MiniProgramLoginRequest;
import com.xama.backend.infrastructure.mediatr.core.Mediator;
import com.xama.backend.infrastructure.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/miniProgram")
public class MiniProgramController {

    private final Mediator mediator;

    @Autowired
    public MiniProgramController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("miniProgramLogin")
    public MiniProgramLoginDto miniProgramLogin(@RequestBody MiniProgramLoginRequest miniProgramLoginRequest,HttpServletRequest request){

        String clientIp = HttpUtil.getIp(request);
        miniProgramLoginRequest.setClientIp(clientIp);
        return this.mediator.dispatch(miniProgramLoginRequest);

    }


}
