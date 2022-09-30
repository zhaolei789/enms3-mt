package cn.ewsd.base.utils;

import cn.ewsd.system.model.ChildTable;
import cn.ewsd.system.model.CodeTable;
import cn.ewsd.system.model.GeneratorConfig;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {


    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/generator/Model.java.vm");
        templates.add("templates/generator/Mapper.java.vm");
        templates.add("templates/generator/Mapper.xml.vm");
        templates.add("templates/generator/Service.java.vm");
        templates.add("templates/generator/ServiceImpl.java.vm");
        templates.add("templates/generator/Controller.java.vm");
        templates.add("templates/generator/index.jsp.vm");
        templates.add("templates/generator/edit.jsp.vm");
        templates.add("templates/generator/menu.sql.vm");
        return templates;
    }

    /**
     * @param structure 页面结构             上下结构:upAndDown  左右结构:about
     * @return
     */

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip, GeneratorConfig generatorConfig) {
        // 第一步：直接读取配置信息
        Configuration config = getConfig();

        // 第二步：读取yml配置文件进行替换
        Map<String, String> mapProps = generatorConfig.getMap();
        if(!mapProps.isEmpty()){
            for (String key : mapProps.keySet()) {
                config.setProperty(key,mapProps.get(key));
            }
        }

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName").toLowerCase());
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        String columnCamelCaseName;

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName").toLowerCase());
            columnCamelCaseName = cn.ewsd.base.utils.StringUtils.camelCaseName(column.get("columnName").toLowerCase());
            columnEntity.setColumnNameFirstUpperCase(columnCamelCaseName.substring(0, 1).toUpperCase() + columnCamelCaseName.substring(1));
            columnEntity.setColumnNameCamelCase(columnCamelCaseName);
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        String systemName = config.getString("systemName");
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname());
        map.put("columns", tableEntity.getColumns());
        map.put("systemName", systemName);
        map.put("SystemName", systemName.substring(0, 1).toUpperCase() + systemName.substring(1));
        map.put("package", config.getString("package"));
        map.put("basepackage", config.getString("basepackage"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(),config)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        //return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
        return cn.ewsd.base.utils.StringUtils.camelCaseName(columnName);
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        String tableCamelCaseName = columnToJava(tableName);
        return tableCamelCaseName.substring(0, 1).toUpperCase() + tableCamelCaseName.substring(1);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, Configuration config) {
        //配置信息
        String packageName = config.getString("package");


        String packagePath = "main" + File.separator + "java" + File.separator;
        String basePackagePath = "main" + File.separator + "resources" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (StringUtils.isNotBlank(basePackagePath)) {
            basePackagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Model.java.vm")) {
            return packagePath + "model" + File.separator + className + ".java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            //return packagePath + "mapper" + File.separator + className + "Mapper.xml";
            return basePackagePath + "mapper" + File.separator + className + "Mapper.xml";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("index.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "index.html";
        }

        if (template.contains("edit.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "edit.html";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }

    public static List<String> getManyTemplates(String structure) {
        List<String> templates = new ArrayList<String>();
        if (structure.equals("upAndDown")) {
            templates.add("templates/many/indexUpanddown.jsp.vm");
        } else {
            templates.add("templates/many/indexAbout.jsp.vm");
        }
        templates.add("templates/many/Controller.java.vm");
        templates.add("templates/many/Model.java.vm");
        templates.add("templates/many/Mapper.java.vm");
        templates.add("templates/many/Mapper.xml.vm");
        templates.add("templates/many/Service.java.vm");
        templates.add("templates/many/ServiceImpl.java.vm");
        templates.add("templates/many/edit.jsp.vm");
        templates.add("templates/many/menu.sql.vm");
        return templates;
    }

    public static List<String> getChildTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/child/Model.java.vm");
        templates.add("templates/child/Mapper.java.vm");
        templates.add("templates/child/Mapper.xml.vm");
        templates.add("templates/child/Service.java.vm");
        templates.add("templates/child/ServiceImpl.java.vm");
        templates.add("templates/child/Controller.java.vm");
        templates.add("templates/child/index.jsp.vm");
        templates.add("templates/child/edit.jsp.vm");
        templates.add("templates/child/menu.sql.vm");
        return templates;
    }

    /**
     * 获取文件名 (多表)
     */
    public static String getFileNameMultTable(String template, String judge, String className, Configuration config) {
        //配置信息
        String packageName = config.getString("package");


        String packagePath = "main" + File.separator + "java" + File.separator;
        String basePackagePath = "main" + File.separator + "resources" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }
        if (StringUtils.isNotBlank(basePackagePath)) {
            basePackagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Model.java.vm")) {
            return packagePath + "model" + File.separator + className + ".java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            //return packagePath + "mapper" + File.separator + className + "Mapper.xml";
            return basePackagePath + "mapper" + File.separator + className + "Mapper.xml";
        }
        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }
        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }
        if (template.contains("edit.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "edit.html";
        }
        if (template.contains("indexAbout.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "index.html";
        }

        if (template.contains("indexUpanddown.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "index.html";
        }
        if (template.contains("edit.jsp.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + config.getString("systemName")
                    + File.separator + className.substring(0, 1).toLowerCase() + className.substring(1) + File.separator + "edit.html";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }
        return null;
    }

    public static void codeMultTable(Map<String, String> mainTable,
                                     List<Map<String, String>> mainColumns,
                                     String judge,
                                     String structure,
                                     String title,
                                     String associated,
                                     List<List<Map<String, String>>> mapList,
                                     CodeTable codeTable,

                                     ZipOutputStream zip, GeneratorConfig generatorConfig) {
        // 第一步：直接读取配置信息
        Configuration config = getConfig();

        // 第二步：读取yml配置文件进行替换
        Map<String, String> mapProps = generatorConfig.getMap();
        if (!mapProps.isEmpty()) {
            for (String key : mapProps.keySet()) {
                config.setProperty(key, mapProps.get(key));
            }
        }


        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(mainTable.get("tableName").toLowerCase());
        tableEntity.setComments(mainTable.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        String columnCamelCaseName;

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : mainColumns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName").toLowerCase());
            columnCamelCaseName = cn.ewsd.base.utils.StringUtils.camelCaseName(column.get("columnName").toLowerCase());
            columnEntity.setColumnNameFirstUpperCase(columnCamelCaseName.substring(0, 1).toUpperCase() + columnCamelCaseName.substring(1));
            columnEntity.setColumnNameCamelCase(columnCamelCaseName);
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);


        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        VelocityContext context = null;
        //封装模板数据
        if (judge.equals("masterTable")) {
            String systemName = config.getString("systemName");
            Map<String, Object> map = new HashMap<>();
            map.put("tableName1", tableEntity.getTableName());
            map.put("comments1", tableEntity.getComments());
            map.put("pk1", tableEntity.getPk());
            map.put("className1", tableEntity.getClassName());
            map.put("classname1", tableEntity.getClassname());
            map.put("pathName1", tableEntity.getClassname());
            map.put("columns1", tableEntity.getColumns());
            map.put("systemName1", systemName);
            map.put("SystemName1", systemName.substring(0, 1).toUpperCase() + systemName.substring(1));
            map.put("package1", config.getString("package"));
            map.put("basepackage1", config.getString("basepackage"));
            map.put("author1", config.getString("author"));
            map.put("email1", config.getString("email"));
            map.put("datetime1", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            map.put("gltitlezb1",codeTable.getTitle()[0]);
            map.put("glgl",codeTable.getAssociated());
            //对子表进行操作
            // 多少个子表  每个子表的列信息 id url 标题


            //列信息
            List<ChildTable> list = new ArrayList<>();
            ChildTable childTable = null;
            List<ColumnEntity> columsLists = null;
            for (int i = 0; i < mapList.size(); i++) {
                String cT[] = codeTable.getChildTable();
                childTable = new ChildTable();
                columsLists = new ArrayList<>();
                for (int ls = 0; ls < mapList.get(i).size(); ls++) {
                    ColumnEntity columnEntity = new ColumnEntity();
                    columnEntity.setColumnName(mapList.get(i).get(ls).get("columnName").toLowerCase());
                    columnCamelCaseName = cn.ewsd.base.utils.StringUtils.camelCaseName(mapList.get(i).get(ls).get("columnName").toLowerCase());
                    columnEntity.setColumnNameFirstUpperCase(columnCamelCaseName.substring(0, 1).toUpperCase() + columnCamelCaseName.substring(1));
                    columnEntity.setColumnNameCamelCase(columnCamelCaseName);
                    columnEntity.setDataType(mapList.get(i).get(ls).get("dataType"));
                    columnEntity.setComments(mapList.get(i).get(ls).get("columnComment"));
                    columnEntity.setExtra(mapList.get(i).get(ls).get("extra"));
                    //列名转换成Java属性名
                    String attrName = columnToJava(columnEntity.getColumnName());
                    columnEntity.setAttrName(attrName);
                    columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
                    //列的数据类型，转换成Java类型
                    String attrType = config.getString(columnEntity.getDataType(), "unknowType");
                    columnEntity.setAttrType(attrType);
                    //是否主键
                    if ("PRI".equalsIgnoreCase(mapList.get(i).get(ls).get("columnKey")) && tableEntity.getPk() == null) {
                        tableEntity.setPk(columnEntity);
                    }
                    columsLists.add(columnEntity);
                }
                childTable.setTitle(codeTable.getTitle()[i+1]);
                childTable.setClouns(columsLists);
                childTable.setPathName(StringUtils.uncapitalize(tableToJava(cT[i+1], config.getString("tablePrefix"))));
                childTable.setClassName(tableToJava(cT[i+1], config.getString("tablePrefix")));
                childTable.setClassname(StringUtils.uncapitalize(tableToJava(cT[i+1], config.getString("tablePrefix"))));
                childTable.setRelation(codeTable.getAssociated()[i]);
                list.add(childTable);
            }
            map.put("list", list);
            context = new VelocityContext(map);
        } else {
            String systemName = config.getString("systemName");
            Map<String, Object> map = new HashMap<>();
            map.put("tableName", tableEntity.getTableName());
            map.put("comments", tableEntity.getComments());
            map.put("pk", tableEntity.getPk());
            map.put("className", tableEntity.getClassName());
            map.put("classname", tableEntity.getClassname());
            map.put("pathName", tableEntity.getClassname());
            map.put("columns", tableEntity.getColumns());
            map.put("systemName", systemName);
            map.put("SystemName", systemName.substring(0, 1).toUpperCase() + systemName.substring(1));
            map.put("package", config.getString("package"));
            map.put("basepackage", config.getString("basepackage"));
            map.put("author", config.getString("author"));
            map.put("email", config.getString("email"));
            map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            map.put("associated", associated);
            map.put("glgl",associated);
            context = new VelocityContext(map);
        }

        //获取模板列表
        List<String> templates = null;
        if (judge.equals("masterTable")) {
            templates = getManyTemplates(structure);
        } else {
            templates = getChildTemplates();
        }
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(judge.equals("masterTable") ? getFileNameMultTable(template, judge, tableEntity.getClassName(), config) : getFileName(template, tableEntity.getClassName(), config)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }


    }
}

