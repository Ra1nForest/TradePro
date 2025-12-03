package com.linn.tradepro.common.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/trade_pro?serverTimezone=Asia/Shanghai&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull", "root", "l1226441618")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("linn") // 设置作者
                            .commentDate("2023-05-20")   //注释日期
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 指定输出目录
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir() //禁止打开输出目录，默认打开
                    ;
                })

                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.linn.tradepro") // 设置父包名
                            .entity("entity") // 设置实体包名
                            .mapper("mapper") // 设置mapper包名
                            .service("service") // 设置service包名
                            .serviceImpl("service.impl") // 设置serviceImpl包名
                            .controller("controller") // 设置controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mappers")); // 设置mapperXml生成路径
                })

                // 策略配置
                .strategyConfig(builder -> {
                    builder//.addInclude("sys_menu") // 设置需要生成的表名
                            //.addTablePrefix("sys_") // 设置过滤表前缀
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
                            .enableFileOverride() // 覆盖已生成文件
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .enableTableFieldAnnotation() //开启实体字段注解
                            // Mapper 策略配置
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class) // 设置Mapper注解
                            .enableFileOverride() // 覆盖已生成文件
                            // Service 策略配置
                            .serviceBuilder()
                            .enableFileOverride() // 覆盖已生成文件
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                            // Controller 策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启 REST 风格
                            .enableFileOverride() // 覆盖已生成文件

                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
