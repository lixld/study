package com.lixl.study;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadRemoteFile {

    public static boolean httpDownload(String httpUrl, String saveFile) {
        // 1.下载网络文件
        int byteRead;
        URL url;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return false;
        }
        try {
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            byte[] buf = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(buf)) !=-1) {
               fileOutputStream.write(buf,0,read);
            }
            inputStream.close();
            fileOutputStream.close();
            /*//2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            InputStream inStream = conn.getInputStream();
            //3.写入文件
            FileOutputStream fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();*/
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static void main(String[] args) {
//        httpDownload("https://jdvideo.300hu.com/vodtransgzp1251412368/5285890780803702625/v.f30.mp4?dockingId=74b89afb-f752-4626-bb88-598b872bbd47&storageSource=3", "/Users/lixl/Desktop/33.mp4");


        httpDownload("https://cdn.itsmrkt.com/images/20200821/1b91cab389fb4ac1a0cf74e94b421795.jpg","/Users/lixl/Desktop/222.jpg");
    }
}
