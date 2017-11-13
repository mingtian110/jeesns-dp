package com.luntan.deppon.core.utils;

import java.util.UUID;

/**
 * Created by cubc-luntan 2017/7/15.
 */
public class TokenUtil {

    public static String getToken(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
