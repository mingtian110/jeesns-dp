package com.luntan.deppon.model.common;

/**
 * @author 380853    mingruishen
 * @create 2018/1/27
 * jeesns
 */


/**
 * ����״̬
 *
 * @author wuxing
 */
public class MqConstants extends MEnum<MqConstants> {

    /**
     * ���к�
     */
    private static final long serialVersionUID = -916709231438329100L;

    public static final MqConstants LUNTAN_MSG_TOPIC     = (MqConstants)create("LUNTAN_MSG_TOPIC",    (byte) 0,	"ϵͳ֪ͨ");
    public static final MqConstants LUNTAN_MSG_TAG     = (MqConstants)create("LUNTAN_MSG_TAG",    (byte) 1,	"ϵͳ֪ͨ");

    /*΢������*/
    public static final MqConstants WEIBO_COMMENT     = (MqConstants)create("WEIBO_COMMENT",    (byte) 2,	"�������������΢��");
    public static final MqConstants WEIBO_LIKE     = (MqConstants)create("WEIBO_LIKE",    (byte) 3,	"����ϲ�������΢��");




}
