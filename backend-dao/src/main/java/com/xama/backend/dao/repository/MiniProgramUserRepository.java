package com.xama.backend.dao.repository;

import com.xama.backend.domain.entity.MiniProgramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniProgramUserRepository extends JpaRepository<MiniProgramUser,Integer> {

    /**
     * 是否已注册
     * @param openId    微信OpenId
     * @return  是否注册
     */
    boolean existsByOpenId(String openId);

    /**
     * 通过Id查找
     * @param id    id
     * @return  小程序用户
     */
    MiniProgramUser getById(String id);

    /**
     * 通过openId查找
     * @param openId    id
     * @return  小程序用户
     */
    MiniProgramUser getByOpenId(String openId);
}
