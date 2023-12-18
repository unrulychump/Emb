package com.example.emb;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 */
public class Generator {

    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");  //获取项目路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOpen(false);
        gc.setSwagger2(true);  //自带swagger注解
        gc.setAuthor("KLE1NBLUE&LackOxy");
        gc.setFileOverride(true);  //覆盖原文件
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://localhost:3306/emb?characterEncoding=utf8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置，生成的entity、controller等都放在这个包下
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("face");
        pc.setParent("com.wsdh.softwaregame");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出Mapper文件名
                return projectPath + "/src/main/resources/mapper/" +
                        tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);  //不在java包下创建xml文件
        templateConfig.setEntity("templates/entity.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);  //Lombok
        strategy.setRestControllerStyle(true);
        strategy.setChainModel(true);
//        strategy.setInclude("**");

        // 下面这行注释掉的话就会给所有表生成
        // strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));  //要映射的表名
        strategy.setControllerMappingHyphenStyle(true);

        // 自动填充
        TableFill create_time = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill update_time = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<TableFill>();
        tableFills.add(create_time);
        tableFills.add(update_time);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        // strategy.setVersionFieldName("version");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }
}
