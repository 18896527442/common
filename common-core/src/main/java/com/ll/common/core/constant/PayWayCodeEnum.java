package com.ll.common.core.constant;

import com.gitee.yongzhuzl.commonutil.util.equals.EqualsUtil;
import org.apache.commons.lang3.StringUtils;

public enum PayWayCodeEnum {

    CASH("CASH", "现金支付"),

    DAIS("DAIS", "代收"),

    perBalance("perBalance", "个人账户"),

    subBalance("subBalance", "补贴账户"),

    accountBalance("accountBalance", "账户支付"),

    subTimes("subTimes", "补贴次数"),
    UnionPay("UnionPay", "银联"),

    SWIPE_FACE("SWIPE_FACE", "人脸支付"),

    SWIPE_CARD("SWIPE_CARD", "刷卡支付"),

    CARD_BAG("CARD_BAG", "卡包支付"),

    FRONT_CODE("FRONT_CODE", "前台码支付"),

    WX_JSAPI("WX_JSAPI", "微信jsapi支付"),

    WX_LITE("WX_LITE", "微信小程序支付"),

    WX_BAR("WX_BAR", "微信条码支付"),

    WX_H5("WX_H5", "微信H5支付"),

    WX_NATIVE("WX_NATIVE", "微信扫码支付"),

    ALI_BAR("ALI_BAR", "支付宝条码支付"),

    ALI_JSAPI("ALI_JSAPI", "支付宝服务窗支付"),

    ALI_APP("ALI_APP", "支付宝 app支付"),

    ALI_PC("ALI_PC", "支付宝 电脑网站支付"),

    ALI_WAP("ALI_WAP", "支付宝 wap支付"),

    ALI_QR("ALI_QR", "支付宝 二维码付款"),
    HIS("HIS", "住院费结算");

    private String code;

    private String message;

    private PayWayCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static PayWayCodeEnum nameOf(String name) {
        for (PayWayCodeEnum value : PayWayCodeEnum.values()) {
            if (StringUtils.equalsIgnoreCase(value.name(), name)) {
                return value;
            }
        }
        return null;
    }

    public static String getMessageByCode(String code) {
        for (PayWayCodeEnum payWayCodeEnum : values()) {
            if (EqualsUtil.isEquals(payWayCodeEnum.getCode(), code)) {
                return payWayCodeEnum.getMessage();
            }
        }
        return null;
    }

}
