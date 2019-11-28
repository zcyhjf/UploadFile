package com.foxhis.getfile.web;

import com.foxhis.getfile.entity.Ip;
import com.foxhis.getfile.service.IpService;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class FileController {

    @Autowired
    IpService ipService;

    @Value("${dirpath}")
    String dirpath;

    @RequestMapping("/getfile")
    //向指定ip发送请求
    public String getfile(String tenantid,String hotelid,String filename) {
        Ip ip = ipService.getIp(tenantid, hotelid);
        String targetIp = ip.getIp();
        String url = "http://"+targetIp+"/file?filename="+filename;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try{
            httpClient.execute(httpPost);
        }catch (Exception e){
            return "发送失败";
        }
        return "发送成功";
    }

    @RequestMapping("/upload")
    public String upload(@RequestParam("file")MultipartFile mpfile, String starttime, HttpServletRequest request){
        //处理上传的文件
        ModelAndView modelAndView = new ModelAndView();
        try {
            String fileName = mpfile.getOriginalFilename();
            String filePath = dirpath+File.separator+fileName;
            File file = new File(filePath);
            FileUtils.copyInputStreamToFile(mpfile.getInputStream(),file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("保存失败");
            return "redirect:result?isSuccess=false";
        }
        Long cost = System.currentTimeMillis()-Long.parseLong(starttime);
        System.out.println("保存成功,耗时"+cost+"ms");
        return  "redirect:result?isSuccess=true&cost="+cost;
    }

    //返回结果
    @RequestMapping("/result")
    public String isSuccess(Boolean isSuccess,Long cost){
        if (isSuccess){
            return "保存成功,耗时"+cost+"ms";
        }else {
            return "文件保存失败";
        }
    }
}