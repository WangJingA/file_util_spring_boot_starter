package com.hzx.file.fileutils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 读取excel文件类
 * @author husir
 * @date 20240421
 */
public class ExcelFileUtil<T>{

    /**
     * 不创建对象的读取excel
     * @param excelFile excel文件
     * @return list
     */
    public static List<Map<Integer, String>> readExcelToList(File excelFile){
        if (Objects.isNull(excelFile)){
            throw new IllegalArgumentException("excel文件为空,或者不存在");
        }
        String excelFileName = excelFile.getName();
        String excelExt = excelFileName.substring(excelFileName.lastIndexOf(".") > 0 ? excelFileName.lastIndexOf(".") : 0);
        if (".xlsx".equalsIgnoreCase(excelExt) || ".xls".equalsIgnoreCase(excelExt) || ".xlsm".equalsIgnoreCase(excelExt)) {
            NoModelDataListener noModelDataListener = new NoModelDataListener();
            EasyExcel.read(excelFile, noModelDataListener).sheet().doRead();
            return noModelDataListener.cachedDataList;
        }else {
            throw new IllegalArgumentException("文件类型不正确,请选择excel文件进行解析");
        }
    }

    @Slf4j
    static class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {

        private List<Map<Integer, String>> cachedDataList = new ArrayList<>();

        @Override
        public void invoke(Map<Integer, String> data, AnalysisContext context) {
            log.info("解析到一条数据:{}", JSON.toJSONString(data));
            cachedDataList.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            log.info("所有数据解析完成！");
        }
    }

    /**
     * 带对象的读取excel文件
     * @param file excel文件
     * @param entityClass excel文件实体类
     * @return List<T>
     */
    public List<T> readExcelDataToEntityList(File file,Class entityClass){
        List<T> excelDataList = new ArrayList<>();
        EasyExcel.read(file,entityClass,new PageReadListener(dataList->{
            if (CollUtil.isNotEmpty((Iterable<?>) dataList)) {
                excelDataList.addAll((Collection<? extends T>) dataList);
            }
        })).doReadAll();
        return excelDataList;
    }


    /**
     * 依靠实体类（带EasyExcel注解）将数据写入excel文件
     * @param fileName 文件名，不带后缀
     * @param sheetName sheet页名称
     * @param dataList  数据列表
     * @param excelModelClass 实体类模板，使用注解生成表头
     * @return
     * @throws IOException
     */
    public  byte[] writeDataToExcel(String fileName, String sheetName, List<T> dataList, Class excelModelClass) throws IOException {
        File excelTemFile = File.createTempFile(fileName, ".xlsx");
        try {
            EasyExcel.write(excelTemFile,excelModelClass).sheet(sheetName).doWrite(dataList);
        }catch (Exception e){
            throw new IOException("写入execl文件失败");
        }
        byte[] readBytes = FileUtil.readBytes(excelTemFile);
        excelTemFile.delete();
        return readBytes;
    }
    /**
     * 不依靠实体类将数据写入excel文件
     * @param modelFile 模板文件
     * @param dataList  数据列表
     * @return
     * @throws IOException
     */
    public byte[] writeDataToExcelWithNoEntity(File modelFile,  LinkedList<List<Object>> dataList) throws IOException {
        File excelTemFile = File.createTempFile("template", ".xlsx");
        try {
            EasyExcel.write(excelTemFile).withTemplate(modelFile).sheet().doWrite(dataList);
        }catch (Exception e){
            throw new IOException("写入execl文件失败");
        }
        byte[] readBytes = FileUtil.readBytes(excelTemFile);
        excelTemFile.delete();
        return readBytes;
    }
}
