package com.xama.backend.infrastructure.utils;

import com.xama.backend.infrastructure.security.EncryptorBeanConfig;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;

public class MiniProgramUtil {
    private static final String APPID = "gbDPdGchnbFyQcsYC0ozLBoYIivSFsQR1nro23FdbWE=";
    private static final String SECRET = "Avo9sOHEUELQwQFfTEMK1Pp0fnEKJk3W2LnQha50s7E25X6lfvR0ZPF6hzHdsam5";
    private static final String GRANT_TYPE = "authorization_code";

    /**
     * 获取微信认证地址
     * @param code  小程序登陆时获取的Code
     * @return  微信认证地址
     */
    public static StringBuffer appendAuthUrl(String code){
        StringBuffer authUrl = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?");

        PooledPBEStringEncryptor pooledPBEStringEncryptor = EncryptorBeanConfig.initStringEncryptor();
        authUrl.append("appid=").append(pooledPBEStringEncryptor.decrypt(APPID));
        authUrl.append("&secret=").append(pooledPBEStringEncryptor.decrypt(SECRET));
        authUrl.append("&js_code=").append(code);
        authUrl.append("&grant_type=").append(GRANT_TYPE);

        return authUrl;
    }
}
