package com.luntan.deppon.model.common;

/**
 * @author 380853    mingruishen
 * @create 2018/1/27
 * jeesns
 */


/**
 * 清算状态
 *
 * @author wuxing
 */
public class MqConstants extends MEnum<MqConstants> {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -916709231438329100L;

    public static final MqConstants LUNTAN_MSG_TOPIC     = (MqConstants)create("LUNTAN_MSG_TOPIC",    (byte) 0,	"系统通知");
    public static final MqConstants LUNTAN_MSG_TAG     = (MqConstants)create("LUNTAN_MSG_TAG",    (byte) 1,	"系统通知");

    /*微博评论*/
    public static final MqConstants WEIBO_COMMENT     = (MqConstants)create("WEIBO_COMMENT",    (byte) 2,	"有人评论了你的微博");
    public static final MqConstants WEIBO_LIKE     = (MqConstants)create("WEIBO_LIKE",    (byte) 3,	"有人喜欢了你的微博");




}
