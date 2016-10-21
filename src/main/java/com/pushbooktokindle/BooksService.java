package com.pushbooktokindle;

import com.alibaba.fastjson.JSONObject;
import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;
import com.google.common.io.Files;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huzhx_000 on 2016/10/16.
 */
@RestController
@RequestMapping("/service")
@EnableAutoConfiguration
public class BooksService {

    public static final int SECCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    public static final int EXISTS_CODE = 1;
    private static int THREAD_COUNT = 3;
    @Autowired
    private ConcurrentHashMap<String, String> booksMap;

    public static final int cache = 10 * 1024;
    @Autowired
    JavaMailSenderImpl sender;

    @RequestMapping("/askBookUrl")
    public String askBookUrl(@RequestBody Map<String, String> voMap) {
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        JSONObject jsonObject = new JSONObject();

        if (booksMap.containsKey(bookName)) {
            jsonObject.put("result", SECCESS_CODE);
            jsonObject.put("bookUrl", booksMap.get(bookName));
            jsonObject.put("kindleMail", kindleMail);
            jsonObject.put("bookName", bookName);
            return jsonObject.toString();
        } else {
            jsonObject.put("result", ERROR_CODE);
            return jsonObject.toString();
        }

//        booksMap.c
    }

    @RequestMapping("/downLoadBook")
    public String downLoadBook(@RequestBody Map<String, String> voMap) {
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        String bookURL = voMap.get("bookURL");
        JSONObject jsonObject = new JSONObject();

        String pathname = "/books" + File.separatorChar + bookName + ".txt";
        httpDownload(bookURL, pathname,jsonObject);
        return jsonObject.toString();

//        booksMap.c
    }

    @RequestMapping("/sendBook")
    public String sendBook(@RequestBody Map<String, String> voMap) {
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        String filepath = voMap.get("filepath");
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(kindleMail)){
            kindleMail = "huzhongying3_66@kindle.cn";
        }
        try {
            MimeMessage mimeMessge = sender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessge,true);

            mimeMessageHelper.setTo(kindleMail);
            mimeMessageHelper.setFrom(sender.getUsername());
            mimeMessageHelper.setSubject("Convert");
            mimeMessageHelper.addAttachment(bookName+".txt",new File(filepath));
            mimeMessageHelper.setText("");
            sender.send(mimeMessge);
            jsonObject.put("result",SECCESS_CODE);
        } catch (MessagingException e) {
            e.printStackTrace();
            jsonObject.put("result",ERROR_CODE);
        }
        return jsonObject.toString();

//        booksMap.c
    }


    public void httpDownload(String path, String filepath, JSONObject jsonObject) {
        jsonObject.put("filepath", filepath);
        if (new File(filepath).exists()) {
            jsonObject.put("result", EXISTS_CODE);
        }else{
            ApplicationManaged applicationManaged = new ApplicationManaged();
            applicationManaged.downLoad(filepath, path);
            jsonObject.put("result", SECCESS_CODE);
        }


    }
//    /**http下载*/
//    public static boolean httpDownload(String httpUrl,String saveFile) {
//        // 下载网络文件
//        int bytesum = 0;
//        int byteread = 0;
//
//        URL url = null;
//        try {
//            url = new URL(httpUrl);
//        } catch (MalformedURLException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//            return false;
//        }
//
//        try {
//            URLConnection conn = url.openConnection();
//            InputStream inStream = conn.getInputStream();
//            FileOutputStream fs = new FileOutputStream(saveFile);
//            byte[] buffer = new byte[1204];
//            while ((byteread = inStream.read(buffer)) != -1) {
//                bytesum += byteread;
//                System.out.println(bytesum);
//                fs.write(buffer, 0, byteread);
//            }
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//
//        }
//    }
}