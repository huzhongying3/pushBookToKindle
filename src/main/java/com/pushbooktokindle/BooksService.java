package com.pushbooktokindle;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
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

    @Autowired
    private   ConcurrentHashMap<String,String> booksMap ;

    public static final int cache = 10 * 1024;
    @RequestMapping("/askBookUrl")
    public String askBookUrl(@RequestBody Map<String,String> voMap){
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        JSONObject jsonObject = new JSONObject();

        if (booksMap.containsKey(bookName)) {
            jsonObject.put("result",0);
            jsonObject.put("bookUrl",booksMap.get(bookName));
            jsonObject.put("kindleMail",kindleMail);
            jsonObject.put("bookName",bookName);
            return  jsonObject.toString();
        }else{
            jsonObject.put("result",-1);
            return jsonObject.toString();
        }

//        booksMap.c
    }

    @RequestMapping("/downLoadBook")
    public String sendBookByUrl(@RequestBody Map<String,String> voMap){
        String bookName = voMap.get("bookName");
        String kindleMail = voMap.get("kindleMail");
        String bookURL = voMap.get("bookURL");
        JSONObject jsonObject = new JSONObject();

        String pathname = "d" + File.pathSeparatorChar + File.separatorChar + "books" + File.separatorChar + bookName+".txt";
        httpDownload(bookURL,pathname);
        return jsonObject.toString();

//        booksMap.c
    }
    public static String download(String url, String filepath) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            if (filepath == null)
                filepath = getFilePath(response);
            File file = new File(filepath);
            file.getParentFile().mkdirs();
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer=new byte[cache];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer,0,ch);
            }
            is.close();
            fileout.flush();
            fileout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**http下载*/
    public static boolean httpDownload(String httpUrl,String saveFile) {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return false;
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }
}