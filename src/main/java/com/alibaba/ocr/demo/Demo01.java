package com.alibaba.ocr.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
    /**
     * 使用APPCODE进行云市场ocr服务接口调用
     */
    class APPCodeDemo {

        /*
         * 获取参数的json对象（jsonObject）
         */
        public static JSONObject getParam(int type, String dataValue) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("dataType", type);
                obj.put("dataValue", dataValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return obj;
        }

        public static void main(String[] args){
            String host = "http://dm-51.data.aliyun.com";
            String path = "/rest/160601/ocr/ocr_idcard.json";
            String appcode = "cd6ac6f631634441a6292920f63df4d4";
            String imgFile = "E:\\hk_daily\\work\\0515\\lbl_20190515163358.jpg";
            Boolean is_old_format = false;//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
            //请根据线上文档修改configure字段
            JSONObject configObj = new JSONObject();
            //在这里实现的就是一种back/face正面和反面的效果的情况
            configObj.put("side", "back");
            String config_str = configObj.toString();
            System.out.println("此刻的配置字符串是："+config_str+"~~~"+configObj.size());

            //            configObj.put("min_size", 5);
            //            String config_str = "";

            String method = "POST";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE cd6ac6f631634441a6292920f63df4d4
            headers.put("Authorization", "APPCODE " + appcode);

            Map<String, String> querys = new HashMap<String, String>();

            // 对图像进行base64编码
            //这里包括了一些非常重要的一些文件的操作
            String imgBase64 = "";
            try {
                File file = new File(imgFile);
                System.out.println(file.getName());
                byte[] content = new byte[(int) file.length()];
                FileInputStream finputstream = new FileInputStream(file);
                finputstream.read(content);
                finputstream.close();
                imgBase64 = new String(encodeBase64(content));
                System.out.println("现在的字节码是："+imgBase64);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            // 拼装请求body的json字符串
            JSONObject requestObj = new JSONObject();
            try {
                if(is_old_format) {
                    JSONObject obj = new JSONObject();
                    obj.put("image", getParam(50, imgBase64));
                    if(config_str.length() > 0) {
                        obj.put("configure", getParam(50, config_str));
                    }
                    JSONArray inputArray = new JSONArray();
                    inputArray.add(obj);
                    requestObj.put("inputs", inputArray);
                }else{
//              is_old_format是否为一个老的格式;走到这里代表现在是一个is_old_format false的boolear
                    requestObj.put("image", imgBase64);
                    if(config_str.length() > 0) {
                        requestObj.put("configure", config_str);
                        System.out.println("现在走的是现在的这个选择分支语句："+requestObj);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String bodys = requestObj.toString();
            System.out.println("现在的body体为："+bodys);

            try {
                /**
                 * 重要提示如下:
                 * HttpUtils请从
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                 * 下载
                 *
                 * 相应的依赖请参照
                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                 */
                HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
                System.out.println("httpUtil这个返回的一个值是什么呢："+response);
                System.out.println("当前的查询的参数："+querys);
                int stat = response.getStatusLine().getStatusCode();
                if(stat != 200){
                    System.out.println("Http code: " + stat);
                    System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                    System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                    return;
                }

                String res = EntityUtils.toString(response.getEntity());
                JSONObject res_obj = JSON.parseObject(res);
                if(is_old_format) {
                    JSONArray outputArray = res_obj.getJSONArray("outputs");
                    String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
                    JSONObject out = JSON.parseObject(output);
                    System.out.println(out.toJSONString());
                }else{
                    System.out.println(res_obj.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
