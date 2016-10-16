package com.pushbooktokindle;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huzhx_000 on 2016/10/16.
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
}
