package top.wwf.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 登录服务器，三种角色均通过此接口登录
     * @param code 微信小程序登录的使用此字段
     * @param userName 网页版登录的使用userName和passWord字段
     * @param passWord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public ServerResponse login(String code,String userName,String passWord){
//        LoginResultVO result =userService.loginServer(code);
//        return ServerResponse.create(result);
        return null;
    }

    /**
     * 获取用户信息，含收货地址，返回前端后，保存到缓存中
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userInfo")
    public ServerResponse getUserInfo(){
        MySession session    =MySession.getInstance();
        String    userOpenId = session.getUserOpenId();
//        UserDetailedInfoVO result=userService.getUserDetailedInfo(userOpenId,wxName);
//        return ServerResponse.create(result);
        return null;
    }

    /**
     * 编辑用户信息，含收货地址
     * @param userDetailedInfoVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editUserInfo")
    public ServerResponse editUserInfo(UserDetailedInfoVO userDetailedInfoVO){
        MySession session=MySession.getInstance();
        String userOpenId=session.getUserOpenId();
//        UserDetailedInfoVO result=userService.editUserInfo(userOpenId,userDetailedInfoVO);
//        return ServerResponse.create(result);
        return null;
    }

    /**
     * 添加新用户，返回注册邀请码；管理员只能通过手动改动数据库进行添加；
     * @param role 新用户的角色，不能为管理员角色
     * @param userName 新用户的用户名，添加成功后，只有管理员可以进行修改
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public ServerResponse addUser(
            @RequestParam(value = "role",defaultValue = "0") int role,
            @RequestParam(value = "userName",defaultValue = "") String userName
    ){
        return null;
    }

    /**
     * 编辑已有用户信息，可编辑的内容仅限：角色、用户名
     * @return
     */
    @ResponseBody
    @RequestMapping("/editUser")
    public ServerResponse editUser(){
        return null;
    }

    /**
     * 删除用户，含有未结束订单的用户无法删除；
     * 用户删除后，如果有上架商品，全部自动下架；
     * @return
     */
    @ResponseBody
    @RequestMapping("delUser")
    public ServerResponse delUser(){
        return null;
    }



}
