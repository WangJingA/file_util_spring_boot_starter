package com.hzx.entity;

/**
 * 图片填充参数
 */
public class ImageBO {

    /**
     * 图片名称
     */
    private String imageType;
    /**
     * 图片横坐标位置
     */
    private float imageXPostion;

    /**
     * 图片纵坐标位置
     */
    private float imageYPosition;
    /**
     * 图片字节数组
     */
    private byte[] imageByte;

    /**
     * pdf文档图片域名
     *
     */
    private String imageFeildName;

    public String getImageFeildName() {
        return imageFeildName;
    }

    public void setImageFeildName(String imageFeildName) {
        this.imageFeildName = imageFeildName;
    }


    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public float getImageXPostion() {
        return imageXPostion;
    }

    public void setImageXPostion(float imageXPostion) {
        this.imageXPostion = imageXPostion;
    }

    public float getImageYPosition() {
        return imageYPosition;
    }

    public void setImageYPosition(float imageYPosition) {
        this.imageYPosition = imageYPosition;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

}
