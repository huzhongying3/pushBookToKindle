package com.pushbooktokindle;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @auth:huzhongying
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@EnableAutoConfiguration
public class RootWeb {
    @RequestMapping("/")
    public String web(){

        return "InputBookName";//返回的内容就是templetes下面文件的名称
    }
}
