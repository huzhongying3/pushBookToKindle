package com.pushbooktokindle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huzhx_000 on 2016/10/16.
 */
@Controller
@EnableAutoConfiguration
public class BooksService {

    @Autowired
    private   ConcurrentHashMap<String,String> booksMap ;

    @RequestMapping("/")
    public String web(Map<String,Object> model){
        model.put("time",new Date());
        model.put("message",booksMap.toString());
        return "InputBookName";//返回的内容就是templetes下面文件的名称
    }
}
