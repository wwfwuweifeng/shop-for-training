package top.wwf.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.modules.user.service.UserService;
import top.wwf.modules.user.vo.LoginResultVO;
import top.wwf.modules.user.vo.UserDetailedInfoVO;

/**
* @Description:    用户相关的接口
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019/2/20 11:20
*/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @ResponseBody
//    @RequestMapping(value = "/login")
//    public ServerResponse login(String code){
//        LoginResultVO result =userService.loginServer(code);
//        return ServerResponse.create(result);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/detailedInfo")
//    public ServerResponse getUserDetailedInfo(String wxName){
//        MySession session    =MySession.getInstance();
//        String    userOpenId = session.getUserOpenId();
//        UserDetailedInfoVO result=userService.getUserDetailedInfo(userOpenId,wxName);
//        return ServerResponse.create(result);
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value = "/editUserInfo")
//    public ServerResponse editUserInfo(UserDetailedInfoVO userDetailedInfoVO){
//        MySession session=MySession.getInstance();
//        String userOpenId=session.getUserOpenId();
//        UserDetailedInfoVO result=userService.editUserInfo(userOpenId,userDetailedInfoVO);
//        return ServerResponse.create(result);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/contractUserInfo")
//    public ServerResponse getDetailedInfoByContractIdAndRole(String contractId,int role){
//        MySession session=MySession.getInstance();
//        String userOpenId=session.getUserOpenId();
//        UserDetailedInfoVO result=userService.getDetailedInfoByContractIdAndRole(userOpenId,contractId,role);
//        return ServerResponse.create(result);
//    }

}
