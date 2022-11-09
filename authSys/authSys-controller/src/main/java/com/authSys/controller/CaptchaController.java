package com.authSys.controller;

import com.authSys.domain.Constants;
import com.authSys.domain.ResponseResult;
import com.authSys.utils.JwtUtil;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CaptchaController {


    /*
    <dependency>
        <groupId>com.github.penggle</groupId>
        <artifactId>kaptcha</artifactId>
        <version>2.3.2</version>
    </dependency>
     */
    @RequestMapping("/captchaImg")
    @ResponseBody
    public ResponseResult getCaptchaImg(HttpSession session){

        // 算数类型的图片验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111,36);

        // 设置几位数运算，默认是2位数运算
        captcha.setLen(2);

        String result = "";
        try{
            result = new Double(Double.parseDouble(captcha.text())).intValue() + "";
        }catch (Exception e){
            result = captcha.text();
        }

//        session.setAttribute("captchaResult", result);

        String code = captcha.toBase64(); // 生成的图片验证码的base64编码

        Map<String,Object> data = new HashMap<>();
        data.put("code", code);

        ResponseResult success = ResponseResult.success(data);
        String token = JwtUtil.getToken(result);
        success.put(Constants.TOKEN, token);

        return success;
    }

   public void google(){
//        System.out.println("hello world");
//
//        // 用于创建字符串类型的图片验证码（就是展示多个字符的那种）
//        Producer captchaProducer = new DefaultKaptcha();
//
//        BufferedImage img = null;
//
//        String result = captchaProducer.createText();
//        session.setAttribute("captchaResult", result); // 保存结果
//
//        img = captchaProducer.createImage(result);
//        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
//        try {
//            ImageIO.write(img, "jpg", os);
//        } catch (IOException e) {
//            System.out.println("happen error");
//            throw new RuntimeException(e);
//        }
//
//        Map<String,Object> data = new HashMap<>();
//        data.put("img", Base64.getEncoder().encode(os.toByteArray()));
//
//        ResponseResult success = ResponseResult.success(data);
//        return success;
//
   }

    /*
    <dependency>
        <groupId>com.github.whvcse</groupId>
        <artifactId>easy-captcha</artifactId>
        <version>1.6.2</version>
    </dependency>
     */
    public void github(){
//        // 算数类型的图片验证码
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111,36);
//
//        // 设置几位数运算，默认是2位数运算
//        captcha.setLen(2);
//
//        String result = "";
//        try{
//            result = new Double(Double.parseDouble(captcha.text())).intValue() + "";
//        }catch (Exception e){
//            result = captcha.text();
//        }
//
//        session.setAttribute("captchaResult", result);
//
//        String code = captcha.toBase64(); // 生成的图片验证码的base64编码
//
//        Map<String,Object> data = new HashMap<>();
//        data.put("code", code);
//        ResponseResult success = ResponseResult.success(data);
//        return success;
    }
}
