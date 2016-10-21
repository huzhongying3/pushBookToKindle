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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    private static int THREAD_COUNT = 3;
    @Autowired
    private ConcurrentHashMap<String, String> booksMap;

    public static final int cache = 10 * 1024;

    @RequestMapping("/askBookUrl")
    public String askBookUrl(@RequestBody Map<String, String> voMap) {
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        JSONObject jsonObject = new JSONObject();

        if (booksMap.containsKey(bookName)) {
            jsonObject.put("result", 0);
            jsonObject.put("bookUrl", booksMap.get(bookName));
            jsonObject.put("kindleMail", kindleMail);
            jsonObject.put("bookName", bookName);
            return jsonObject.toString();
        } else {
            jsonObject.put("result", -1);
            return jsonObject.toString();
        }

//        booksMap.c
    }

    @RequestMapping("/downLoadBook")
    public String sendBookByUrl(@RequestBody Map<String, String> voMap) {
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        String bookURL = voMap.get("bookURL");
        JSONObject jsonObject = new JSONObject();

        String pathname = "d:" + File.separatorChar + "books" + File.separatorChar + bookName + ".txt";
        httpDownload(bookURL, pathname);
        return jsonObject.toString();

//        booksMap.c
    }

    public void httpDownload(String path, String filepath) {

        ApplicationManaged applicationManaged = new ApplicationManaged();
        applicationManaged.downLoad(filepath, path);


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