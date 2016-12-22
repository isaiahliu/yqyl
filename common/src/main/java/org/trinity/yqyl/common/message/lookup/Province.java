package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum Province implements ILookupMessage<LookupType> {
    北京市("11"),
    天津市("12"),
    河北省("13"),
    山西省("14"),
    内蒙古自治区("15"),
    辽宁省("21"),
    吉林省("22"),
    黑龙江省("23"),
    上海市("31"),
    江苏省("32"),
    浙江省("33"),
    安徽省("34"),
    福建省("35"),
    江西省("36"),
    山东省("37"),
    河南省("41"),
    湖北省("42"),
    湖南省("43"),
    广东省("44"),
    广西壮族自治区("45"),
    海南省("46"),
    重庆市("50"),
    四川省("51"),
    贵州省("52"),
    云南省("53"),
    西藏自治区("54"),
    陕西省("61"),
    甘肃省("62"),
    青海省("63"),
    宁夏回族自治区("64"),
    新疆维吾尔自治区("65"),
    台湾省("71"),
    香港特别行政区("81"),
    澳门特别行政区("82");

    private final String messageCode;

    private Province(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.PROVINCE_CODE;
    }
}
