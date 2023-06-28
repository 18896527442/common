package com.ll.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author lilin
 * @desc: TODO
 * @date 2022/8/1811:14
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.20.223:3306/logistics_dev",
                        "giga_tester", "YF_Giga@0331")

                .globalConfig(builder -> {
                    builder.author("lilin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Desktop"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.gigacloud.starcloud") // 设置父包名
                            //.moduleName("smart-interface-common") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Desktop")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_inventory_adjust","t_inventory_adjust_line"); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
