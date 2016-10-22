package com.pushbooktokindle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huzhx_000 on 22016/10/16.
 */
@Configuration
public class AppConfig {
    @Bean   // 将SgtPeppers注册为 SpringContext中的bean
    public ConcurrentHashMap  booksPropertiesFactoryBean() {

        try {
            Properties properties  = new Properties();
            InputStream inputStream =    this.getClass().getClassLoader().getResourceAsStream("books.properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            properties.load(bf);
            inputStream.close(); // 关闭流

            return new ConcurrentHashMap(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Bean
    public FreeMarkerViewResolver viewResolver(){
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
        freeMarkerViewResolver.setContentType("text/html;charset=utf-8");
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setOrder(0);
        freeMarkerViewResolver.setRequestContextAttribute("request");
        return  freeMarkerViewResolver;
    }

    @Bean
    public JavaMailSenderImpl sender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.yahoo.com");
        javaMailSender.setUsername("huzhongying3@yahoo.com");
        javaMailSender.setPassword("xk53fdab1991");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        javaMailSender.setJavaMailProperties(props);
        return  javaMailSender;
    }
}
