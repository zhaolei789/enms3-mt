package cn.ewsd.system.service.impl;

import cn.ewsd.base.utils.GenUtils;
import cn.ewsd.system.mapper.GeneratorMapper;
import cn.ewsd.system.model.CodeTable;
import cn.ewsd.system.model.GeneratorConfig;
import cn.ewsd.system.service.GeneratorService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service("generatorServiceimpl")
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private GeneratorMapper generatorMapper;

    @Autowired
    private GeneratorConfig generatorConfig;

    @Override
    public List<HashMap<String, Object>> getPageSet(String filterSort) {
        return generatorMapper.getPageSet(filterSort);
    }

    @Override
    public List<HashMap<String, Object>> queryList(HashMap<String, Object> map) {
        return generatorMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return generatorMapper.queryTotal(map);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    @Override
    public Map<String, String> queryTableBySqlserver(String tableName) {
        return generatorMapper.queryTableBySqlserver(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        List<Map<String, String>> columns = generatorMapper.queryColumns(tableName);
        return columns;
    }

    @Override
    public List<Map<String, String>> queryColumnsBySqlserver(String tableName) {
        List<Map<String, String>> columns = generatorMapper.queryColumnsBySqlserver(tableName);
        return columns;
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            tableName = tableName.toLowerCase();
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip, generatorConfig);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public List<HashMap<String, Object>> getPageSetBySqlserver(String filterSort) {
        return generatorMapper.getPageSetBySqlserver(filterSort);
    }

    @Override
    public byte[] generatorCodeBySqlserver(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            tableName = tableName.toLowerCase();
            //查询表信息
            Map<String, String> table = queryTableBySqlserver(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumnsBySqlserver(tableName);
            //生成代码
               GenUtils.generatorCode(table, columns, zip,generatorConfig);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] codeMultTable(CodeTable codeTable) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //遍历主子表名
        for (int i=0;i<codeTable.getChildTable().length;i++) {
            //判断是子表还是主表
            String judge=i==0?"masterTable":"childTable";
            //查询表信息
            Map<String, String> mainTable = queryTable(codeTable.getChildTable()[i].toLowerCase());
            //查询列信息
            List<Map<String, String>> mainColumns = queryColumns(codeTable.getChildTable()[i].toLowerCase());

            //处理子表所有的列信息 传过去  主表界面用
            List<List<Map<String, String>>> lists=new ArrayList<>();
            List<Map<String, String>> mapList=null;
            for(int j=1;j<codeTable.getChildTable().length;j++){
                mapList=new ArrayList<>();
                mapList=queryColumns(codeTable.getChildTable()[j].toLowerCase());
                lists.add(mapList);
            }
            //主表没有此字段
            String  associated="";
            if(i!=0){
                associated= codeTable.getAssociated()[i-1];
            }
            GenUtils.codeMultTable(mainTable, mainColumns,judge, codeTable.getStructure(),codeTable.getTitle()[i],associated,lists, codeTable,zip,generatorConfig);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public List<HashMap<String, Object>> getAll(String filterSort) {
        return generatorMapper.getAll(filterSort);
    }


}
