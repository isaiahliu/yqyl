package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum District implements ILookupMessage<LookupType> {
    山东省("37"),

    山东省_泰安市(District.山东省, DistrictType.CITY, "09"),

    山东省_泰安市_市辖区(District.山东省_泰安市, DistrictType.DISTRICT, "01"), // 市辖区

    山东省_泰安市_泰山区(District.山东省_泰安市, DistrictType.DISTRICT, "02"), // 泰山区
    山东省_泰安市_泰山区_岱庙街道(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "001"), // 岱庙街道
    山东省_泰安市_泰山区_财源街道(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "002"), // 财源街道
    山东省_泰安市_泰山区_泰前街道(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "003"), // 泰前街道
    山东省_泰安市_泰山区_上高街道(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "004"), // 上高街道
    山东省_泰安市_泰山区_徐家楼街道(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "005"), // 徐家楼街道
    山东省_泰安市_泰山区_省庄镇(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "100"), // 省庄镇
    山东省_泰安市_泰山区_邱家店镇(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "101"), // 邱家店镇
    山东省_泰安市_泰山区_大津口乡(District.山东省_泰安市_泰山区, DistrictType.SUB_DISTRICT, "202"), // 大津口乡

    山东省_泰安市_岱岳区(District.山东省_泰安市, DistrictType.DISTRICT, "03"), // 岱岳区
    山东省_泰安市_岱岳区_粥店街道(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "001"), // 粥店街道
    山东省_泰安市_岱岳区_天平街道(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "002"), // 天平街道
    山东省_泰安市_岱岳区_山口镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "100"), // 山口镇
    山东省_泰安市_岱岳区_祝阳镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "101"), // 祝阳镇
    山东省_泰安市_岱岳区_范镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "102"), // 范镇
    山东省_泰安市_岱岳区_角峪镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "103"), // 角峪镇
    山东省_泰安市_岱岳区_徂徕镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "104"), // 徂徕镇
    山东省_泰安市_岱岳区_北集坡镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "105"), // 北集坡镇
    山东省_泰安市_岱岳区_满庄镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "106"), // 满庄镇
    山东省_泰安市_岱岳区_夏张镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "107"), // 夏张镇
    山东省_泰安市_岱岳区_道朗镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "108"), // 道朗镇
    山东省_泰安市_岱岳区_黄前镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "109"), // 黄前镇
    山东省_泰安市_岱岳区_大汶口镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "110"), // 大汶口镇
    山东省_泰安市_岱岳区_马庄镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "111"), // 马庄镇
    山东省_泰安市_岱岳区_房村镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "112"), // 房村镇
    山东省_泰安市_岱岳区_良庄镇(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "113"), // 良庄镇
    山东省_泰安市_岱岳区_下港乡(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "200"), // 下港乡
    山东省_泰安市_岱岳区_化马湾乡(District.山东省_泰安市_岱岳区, DistrictType.SUB_DISTRICT, "201"), // 化马湾乡

    山东省_泰安市_宁阳县(District.山东省_泰安市, DistrictType.DISTRICT, "21"), // 宁阳县
    山东省_泰安市_宁阳县_宁阳镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "100"), // 宁阳镇
    山东省_泰安市_宁阳县_泗店镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "101"), // 泗店镇
    山东省_泰安市_宁阳县_东疏镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "102"), // 东疏镇
    山东省_泰安市_宁阳县_伏山镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "103"), // 伏山镇
    山东省_泰安市_宁阳县_堽城镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "105"), // 堽城镇
    山东省_泰安市_宁阳县_蒋集镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "107"), // 蒋集镇
    山东省_泰安市_宁阳县_磁窑镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "109"), // 磁窑镇
    山东省_泰安市_宁阳县_华丰镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "110"), // 华丰镇
    山东省_泰安市_宁阳县_葛石镇(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "111"), // 葛石镇
    山东省_泰安市_宁阳县_鹤山乡(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "201"), // 鹤山乡
    山东省_泰安市_宁阳县_东庄乡(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "204"), // 东庄乡
    山东省_泰安市_宁阳县_乡饮乡(District.山东省_泰安市_宁阳县, DistrictType.SUB_DISTRICT, "206"), // 乡饮乡

    山东省_泰安市_东平县(District.山东省_泰安市, DistrictType.DISTRICT, "23"), // 东平县
    山东省_泰安市_东平县_州城镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "100"), // 州城镇
    山东省_泰安市_东平县_沙河站镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "101"), // 沙河站镇
    山东省_泰安市_东平县_彭集镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "102"), // 彭集镇
    山东省_泰安市_东平县_东平镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "104"), // 东平镇
    山东省_泰安市_东平县_老湖镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "106"), // 老湖镇
    山东省_泰安市_东平县_银山镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "107"), // 银山镇
    山东省_泰安市_东平县_斑鸠店镇(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "108"), // 斑鸠店镇
    山东省_泰安市_东平县_接山乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "200"), // 接山乡
    山东省_泰安市_东平县_大羊乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "202"), // 大羊乡
    山东省_泰安市_东平县_梯门乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "203"), // 梯门乡
    山东省_泰安市_东平县_新湖乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "204"), // 新湖乡
    山东省_泰安市_东平县_商老庄乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "206"), // 商老庄乡
    山东省_泰安市_东平县_戴庙乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "207"), // 戴庙乡
    山东省_泰安市_东平县_旧县乡(District.山东省_泰安市_东平县, DistrictType.SUB_DISTRICT, "208"), // 旧县乡

    山东省_泰安市_新泰市(District.山东省_泰安市, DistrictType.DISTRICT, "82"), // 新泰市
    山东省_泰安市_新泰市_青云街道(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "001"), // 青云街道
    山东省_泰安市_新泰市_新汶街道(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "002"), // 新汶街道
    山东省_泰安市_新泰市_东都镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "101"), // 东都镇
    山东省_泰安市_新泰市_小协镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "102"), // 小协镇
    山东省_泰安市_新泰市_翟镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "103"), // 翟镇
    山东省_泰安市_新泰市_泉沟镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "104"), // 泉沟镇
    山东省_泰安市_新泰市_羊流镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "105"), // 羊流镇
    山东省_泰安市_新泰市_果都镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "106"), // 果都镇
    山东省_泰安市_新泰市_西张庄镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "107"), // 西张庄镇
    山东省_泰安市_新泰市_天宝镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "108"), // 天宝镇
    山东省_泰安市_新泰市_楼德镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "110"), // 楼德镇
    山东省_泰安市_新泰市_禹村镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "111"), // 禹村镇
    山东省_泰安市_新泰市_宫里镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "112"), // 宫里镇
    山东省_泰安市_新泰市_谷里镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "113"), // 谷里镇
    山东省_泰安市_新泰市_石莱镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "114"), // 石莱镇
    山东省_泰安市_新泰市_放城镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "115"), // 放城镇
    山东省_泰安市_新泰市_刘杜镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "116"), // 刘杜镇
    山东省_泰安市_新泰市_汶南镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "117"), // 汶南镇
    山东省_泰安市_新泰市_龙廷镇(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "119"), // 龙廷镇
    山东省_泰安市_新泰市_岳家庄乡(District.山东省_泰安市_新泰市, DistrictType.SUB_DISTRICT, "203"), // 岳家庄乡

    山东省_泰安市_肥城市(District.山东省_泰安市, DistrictType.DISTRICT, "83"), // 肥城市
    山东省_泰安市_肥城市_新城街道(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "001"), // 新城街道
    山东省_泰安市_肥城市_老城镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "100"), // 老城镇
    山东省_泰安市_肥城市_潮泉镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "101"), // 潮泉镇
    山东省_泰安市_肥城市_王瓜店镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "102"), // 王瓜店镇
    山东省_泰安市_肥城市_桃园镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "103"), // 桃园镇
    山东省_泰安市_肥城市_王庄镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "104"), // 王庄镇
    山东省_泰安市_肥城市_湖屯镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "105"), // 湖屯镇
    山东省_泰安市_肥城市_石横镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "106"), // 石横镇
    山东省_泰安市_肥城市_安临站镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "107"), // 安临站镇
    山东省_泰安市_肥城市_孙伯镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "108"), // 孙伯镇
    山东省_泰安市_肥城市_安驾庄镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "109"), // 安驾庄镇
    山东省_泰安市_肥城市_汶阳镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "110"), // 汶阳镇
    山东省_泰安市_肥城市_边院镇(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "111"), // 边院镇
    山东省_泰安市_肥城市_仪阳乡(District.山东省_泰安市_肥城市, DistrictType.SUB_DISTRICT, "200"); // 仪阳乡

    public static enum DistrictType {
        PROVINCE,
        CITY,
        DISTRICT,
        SUB_DISTRICT;
    }

    private final DistrictType districtType;
    private final String messageCode;
    private final District parent;

    private District(final District parent, final DistrictType districtType, final String messageCode) {
        this.parent = parent;
        this.districtType = districtType;
        if (parent == null) {
            this.messageCode = messageCode;
        } else {
            this.messageCode = parent.getMessageCode() + messageCode;
        }
    }

    private District(final String messageCode) {
        this(null, DistrictType.PROVINCE, messageCode);
    }

    public DistrictType getDistrictType() {
        return districtType;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.DISTRICT_CODE;
    }

    public District getParent() {
        return parent;
    }

}
