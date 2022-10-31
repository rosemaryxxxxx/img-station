package com.xhh.imgdemo.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class imgUtils {

    public static String printImageTags(File file) throws ImageProcessingException, Exception {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Double lat = null;
        Double lng = null;
        String data = "";
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
//                log.error("标签名： " + tagName);
//                System.out.println("标签名： " + tagName);
                String desc = tag.getDescription(); //标签信息
                if (tagName.equals("Image Height")) {
                    data += desc + " ";
                    System.out.println("图片高度: " + desc);
                } else if (tagName.equals("Image Width")) {
                    data += desc + " ";
                    System.out.println("图片宽度: " + desc);
                } else if (tagName.equals("Date/Time Original")) {
                    data += desc + " ";
                    System.out.println("拍摄时间: " + desc);
                } else if (tagName.equals("GPS Latitude")) {
                    data += desc + " ";
                    System.out.println("纬度: " + desc);
                    System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
                    lat = latLng2Decimal(desc);
                } else if (tagName.equals("GPS Longitude")) {
                    data += desc + " ";
                    System.out.println("经度: " + desc);
                    System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
                    lng = latLng2Decimal(desc);
                }
            }
        }
        convertGpsToLoaction(lat,lng);
        return data;
    }

    /**
     * 将MultipartFile转换为File
     * @param multiFile
     * @return
     */
    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     *
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }

    /***
     * 经纬度坐标格式转换（* °转十进制格式）
     * @param gps
     */
    public static double latLng2Decimal(String gps) {
        String a = gps.split("°")[0].replace(" ", "");
        String b = gps.split("°")[1].split("'")[0].replace(" ", "");
        String c = gps.split("°")[1].split("'")[1].replace(" ", "").replace("\"", "");
        double gps_dou = Double.parseDouble(a) + Double.parseDouble(b) / 60 + Double.parseDouble(c) / 60 / 60;
        return gps_dou;
    }

    /**
     * api_key：注册的百度api的key
     * coords：经纬度坐标
     * http://api.map.baidu.com/reverse_geocoding/v3/?ak="+api_key+"&output=json&coordtype=wgs84ll&location="+coords
     * <p>
     * 经纬度转地址信息
     *
     * @param gps_latitude  维度
     * @param gps_longitude 精度
     */
    private static String convertGpsToLoaction(double gps_latitude, double gps_longitude) throws IOException {
        String apiKey = "98BTkroQl0ttEO1hgkrP3HZj4DM46gil";
        String loc = "";
        String res = "";
        String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=" + apiKey + "&output=json&coordtype=wgs84ll&location=" + gps_latitude + "," + gps_longitude;
        System.err.println("【url】" + url);

        res = getURLContent(url);
        JSONObject object = JSONObject.parseObject(String.valueOf(res));
        if (object.containsKey("result")) {
            JSONObject result = object.getJSONObject("result");
            if (result.containsKey("addressComponent")) {
                JSONObject address = object.getJSONObject("result").getJSONObject("addressComponent");
                System.err.println("拍摄地点：" + address.get("country") + " " + address.get("province") + " " + address.get("city") + " " + address.get("district") + " "
                        + address.get("street") + " " + result.get("formatted_address") + " " + result.get("business"));
                loc = address.get("country").toString() + address.get("province").toString() + address.get("city").toString()  + address.get("district").toString() + address.get("street").toString()
                        + " " + result.get("formatted_address").toString() + " " + result.get("business").toString();
            }
        }
        return loc;
    }

    /**
     * 获取url中的json内容
     * @param url
     * @return
     */
    public static String getURLContent(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            // 设置为utf-8的编码 解决中文乱码
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 获取图片拍摄时间
     * @param file
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     */
    public static String getImgTime(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        String desc = null;
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                desc = tag.getDescription();
                if (tagName.equals("Date/Time Original")) {
                    System.out.println("拍摄时间: " + desc);
                }
            }
        }
        return desc;
    }

    /**
     * 获取图片拍摄地址
     * @param file
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     */
    public static String getImgLocation(File file) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        String desc = null;
        Double lat = null;
        Double lng = null;
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                desc = tag.getDescription(); //标签信息
                if (tagName.equals("GPS Latitude")) {
                    lat = latLng2Decimal(desc);
                } else if (tagName.equals("GPS Longitude")) {
                    lng = latLng2Decimal(desc);
                }
            }
        }
        return convertGpsToLoaction(lat,lng);
    }

    /**
     * 获取图片存储路径，这里只做本地上的操作，以后可以用图片服务器
     * @param file
     * @return
     */
    public static String getImgUrl(File file) throws IOException {
//        String filePath = file.getCanonicalPath();
//        System.out.println("filePath: " + file.getName());
        return "D:\\code\\imgs\\";
//        return filePath;
    }







}
