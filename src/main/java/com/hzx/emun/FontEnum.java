package com.hzx.emun;

/**
 * 字体枚举类
 */
public enum FontEnum {
    /**
     * 方正舒体
     */
    FZSTK("FZShuTi","/font/FZSTK.TTF"),
    /**
     * 方正姚体
     */
    FZYTK("FZYaoTi","/font/FZYTK.TTF"),
    /**
     * 仿宋
     */
    FSTK("FangSong","/font/simfang.ttf"),
    /**
     * 黑体
     */
    HTTK("SimHei","/font/simhei.ttf"),
    /**
     * 华文彩云
     */
    HWCYTK("STCaiyun","/font/STCAIYUN.TTF"),
    /**
     * 华文琥珀
     */
    HWHPTK("STHupo","/font/STHUPO.TTF"),
    /**
     * 华文楷体
     */
    HWKTTK("STKAITI.TTF","/font/STKaiti"),
    /**
     * 华文仿宋
     */
    HWFSTK("STFANGSO.TTF","/font/FSTFangsong"),
    /**
     * 华文隶书
     */
    HWLSTK("STLiti","/font/STLITI.TTF"),
    /**
     * 华文宋体
     */
    HWSTTK("STSong","/font/STSONG.TTF"),
    /**
     * 华文细黑
     */
    HWXHTK("STXinwei","/font/STXINWEI.TTF"),
    /**
     * 华文新魏
     */
    HWXWTK("STSong","/font/STSONG.TTF"),
    /**
     * 华文行楷
     */
    HWXKTK("STXingkai","/font/STXINGKA.TTF"),
    /**
     * 华文中宋
     */
    HWZSTK("STZhongsong","/font/STZHONGS.TTF"),
    /**
     * 楷体
     */
    KTTK("KaiTi","/font/simkai.ttf"),
    /**
     * 隶书
     */
    LSTK("LiSu","/font/SIMLI.TTF"),
    /**
     * 宋体
     */
    STTK("STSong","/SimSun/simsun.ttc"),
    /**
     * 微软雅黑
     */
    WRYHTK("MicrosoftYaHei","/font/msyh.ttc"),
    /**
     * 微软雅黑Light
     */
    WRYHLTK("MicrosoftYaHeiLight","/font/msyhl.ttc"),
    /**
     * 微软雅黑粗体
     */
    WRYHCTTK("MicrosoftYaHei,Bold","/font/msyhbd.ttc"),
    /**
     * 新宋体
     */
    XSTTK("NSimSun","/font/simsun.ttc"),
    /**
     * 幼圆
     */
    YYTK("YouYuan","/font/SIMYOU.TTF")
    ;

    public String getFontName() {
        return fontName;
    }

    public String getFontPath() {
        return fontPath;
    }

    private final String fontName;
    private final String fontPath;

    FontEnum(String fontName, String fontPath) {
        this.fontName = fontName;
        this.fontPath = fontPath;
    }

    @Override
    public String toString() {
        return "FontEnum{" + "fontName='" + fontName + '\'' + ", fontPath='" + fontPath + '\'' + "} " + super.toString();
    }

}
