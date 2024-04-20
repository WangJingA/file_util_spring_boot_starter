package com.hzx.file.fileutils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.hzx.emun.FontEnum;
import com.hzx.entity.ImageBO;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.util.*;

/**
 * pdf
 * 盖章工具类
 * 字体请参考resource目录下font下img.img
 * @author hzx
 * @date 2024/04/20
 */
public class PdfSignSealUtil {
    /**
     * 依靠文件的填充域填充文字和图片
     * @param pdfFile pdf文件
     * @param textMap 文字键值对，k：pdf文件的文字填充域名，v:填充的文字
     * @param imageList 图片填充参数
     * @param fileName 文件名称，不要加后缀
     * @param fontEnum 字体枚举类
     * @return 盖章后的pdf字节数组
     */
    public static byte[] pdfSignSealByFeildName(File pdfFile, Map<String,String> textMap,
                                                List<ImageBO> imageList, String fileName,
                                                FontEnum fontEnum) throws IOException, DocumentException {

        // 创建临时文件
        File tempFile = File.createTempFile(StrUtil.isNotEmpty(fileName) ? fileName : "templatePdf", ".pdf");
        FileOutputStream fos = new FileOutputStream(tempFile);
        InputStream inputStream = FileUtil.getInputStream(pdfFile);
        PdfReader pdfReader = new PdfReader(inputStream);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
        // 获取pdf表单
        AcroFields acroFields = pdfStamper.getAcroFields();
        // 如果未指定字体，则使用默认字体华文楷体
        BaseFont font = defaultFont();
        // 遍历表单域,填充文本
        if (Objects.nonNull(acroFields)) {
            if (CollUtil.isNotEmpty(textMap)) {
                textMap.forEach((k, v) -> {
                    try {
                        acroFields.setField(k,v);
                        acroFields.setFieldProperty(k,"textfont",font,null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                });
            }
            // 遍历表单域,填充图片
            if (CollUtil.isNotEmpty(imageList)) {
                    if (Objects.nonNull(acroFields)) {
                        imageList.forEach(image -> {
                            byte[] bytes = image.getImageByte();
                            if (bytes != null && image.getImageXPostion() > 0 && image.getImageYPosition() > 0) {
                                Image imageParam = null;
                                try {
                                    imageParam = Image.getInstance(bytes);
                                } catch (BadElementException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageParam.setAbsolutePosition(image.getImageXPostion(), image.getImageYPosition());
                                PdfContentByte underContent = pdfStamper.getOverContent(1);//  getOverContent 图片会覆盖在文字上层
                                imageParam.scaleAbsolute(80, 120);// 指定图片的宽高
                                try {
                                    underContent.addImage(imageParam);
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
            }
        }
        pdfStamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
        pdfStamper.close();
        byte[] resultByte = FileUtil.readBytes(tempFile);
        tempFile.delete();
        return resultByte;
    }

    /**
     * 默认字体
     * @return PdfFont
     * @throws IOException
     */
    private static BaseFont defaultFont() {
        // 默认华文楷体
        BaseFont baseFont;
        try {
            baseFont = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException("无法获取默认字体：" + e.getMessage(),e);
        }
        return baseFont;
    }

    /**
     * 选择的字体
     * @return PdfFont
     * @throws IOException
     */
    private static BaseFont chooseFont(FontEnum fontEnum) {
        // 默认华文楷体
        BaseFont baseFont;
        try {
            baseFont = BaseFont.createFont(fontEnum.getFontName(), "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException("无法获取默认字体：" + e.getMessage(),e);
        }
        return baseFont;
    }
}
