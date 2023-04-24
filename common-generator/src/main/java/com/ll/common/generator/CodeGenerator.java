package com.ll.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author xueguoping
 * @desc: TODO
 * @date 2022/8/1811:14
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://mysql.smt.com:3306/smart_ordering",
                        "smart", "i&Z3LjT6oDu&2ajz")
        /*FastAutoGenerator.create("jdbc:sqlserver://192.168.101.122:1433;DatabaseName=THIS4",
                        "sa", "Smart123456!")*/
                .globalConfig(builder -> {
                    builder.author("xueguoping") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/xueguoping/generator"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.smart.interf.mapper") // 设置父包名
                            //.moduleName("smart-interface-common") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/xueguoping/generator")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("his_batch","his_dept_info","his_dept_info_history","his_district_info",
                            "his_district_info_history","his_patient_docadvices","his_patient_docadvices_history","his_patient_info","his_patient_info_history"); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
