package top.wwf.modules.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.wwf.common.base.ServerResponse;


import top.wwf.modules.web.service.WebService;



/**
* @Description:    web页面相关的接口
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-03-16 22:27
*/
@Controller
//@RequestMapping("/vpr-electronic-contract") //本地版
public class WebController {

    @Autowired
    private WebService webService;

    /**
     * 用过网页上传文件，目前只支持上传模板的docx文件（创建或修改后的均可）
     * @param uploadCheckCode
     * @param uploadFile
     * @return
     */
    @ResponseBody
    @RequestMapping("/uploadFile")
    public ServerResponse uploadFile(String uploadCheckCode, MultipartFile uploadFile){
        webService.uploadFile(uploadCheckCode,uploadFile);
        return ServerResponse.create();
    }


    /**
     * 下载的html页面
     * @return
     */
    @RequestMapping("/view/downFile")// /view/download
    public String downloadJSP(@RequestParam(value = "downloadCheckCode") String downloadCheckCode,Model model){
        model.addAttribute("template",1);
        return "download";
    }
}
