package com.springboot.springboot.controller;
import com.springboot.springboot.utils.MailUtil;
import com.springboot.springboot.utils.ResultVO;
import com.springboot.springboot.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/send")
@Api(value = "send",description = "API")
public class MailController {

    @Autowired
   private MailUtil mailUtil;

    @GetMapping("/text")
    @ApiOperation(value="纯文本发送", notes="纯文本发送")
    public ResultVO sendText(String to, String subject, String content){
        mailUtil.sendSimpleMail(to,subject,content);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/html")
    @ApiOperation(value="HTML发送", notes="HTML发送")
    public ResultVO sendHtml(String to, String subject, String content){
        mailUtil.sendHTMLMail(to,subject,content);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/fujian")
    @ApiOperation(value="附件发送", notes="附件发送")
    public ResultVO sendFujian(String to, String subject, String content, String file1,String file2){
        mailUtil.sendAttachmentMail(to,subject,content,file1,file2);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/pic")
    @ApiOperation(value="发送带图片", notes="发送带图片")
    public ResultVO sendPic(String to, String subject, String content,Map<String, String> imgMap){
        imgMap.put("img01", "src/main/resources/static/image/1.png");
        imgMap.put("img02", "src/main/resources/static/image/2.png");
        mailUtil.sendInlineResourceMail(to,subject,content,imgMap);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/template")
    @ApiOperation(value="模板发送", notes="模板发送")
    public ResultVO sendTemplate(String to, String subject, Map<String, Object> paramMap, String template){
        paramMap.put("name","mazhao");
        paramMap.put("age","20");
        mailUtil.sendTemplateMail(to, subject, paramMap, template);
        return ResultVOUtil.success(true);
    }











}
