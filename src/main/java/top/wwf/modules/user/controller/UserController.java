package top.wwf.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import top.wwf.common.base.MySession;
import top.wwf.common.base.ServerResponse;
import top.wwf.common.page.PageBean;
import top.wwf.modules.user.dto.UserInfoDTO;
import top.wwf.modules.user.entity.SFTUserPersonalInfo;
import top.wwf.modules.user.service.UserService;
import top.wwf.modules.user.vo.RegisterUserVO;
import top.wwf.modules.user.vo.UserInfoVO;

/**
* @Description:    TODO
* @Author:         wwf（hitwh_wwf@163.com）
* @CreateDate:     2019-07-08 15:16
*/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录服务器，三种角色均通过此接口登录
     * @param code 微信小程序登录的使用此字段
     * @param account 网页版登录的使用userName和passWord字段
     * @param passWord
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login")
    public ServerResponse login(String code,String account,String passWord){
        UserInfoVO result =userService.loginServer(code, account, passWord);
        return ServerResponse.create(result);
    }

    /**
     * 获取用户信息，含收货地址，返回前端后，保存到缓存中，此接口暂时不需要
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userInfo")
    public ServerResponse getUserInfo(){
//        MySession session    =MySession.getInstance();
//        String    userId = session.getUserId();
//        UserDetailedInfoVO result=userService.getUserDetailedInfo(userOpenId,wxName);
//        return ServerResponse.create(result);
        return null;
    }

    /**
     * 编辑用户信息，含收货地址，返回编辑后最新的用户个人信息，前端记得更新缓存
     * @param userInfoDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editUserInfo")
    public ServerResponse editUserInfo(UserInfoDTO userInfoDTO){
        MySession           session =MySession.getInstance();
//        String              userId  =session.getUserId();
        SFTUserPersonalInfo result  =userService.editUserInfo(session, userInfoDTO);
        return ServerResponse.create(result);
    }

    /**
     * 管理员添加新用户，返回生成的注册码（也可理解为用户账号）；管理员账号只能通过手动改动数据库进行添加；
     * @param role 新用户的角色，不能为管理员角色
     * @param userName 新用户的用户名，添加成功后，只有管理员可以进行修改
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUserByManager")
    public ServerResponse addUserByManager(
            @RequestParam(value = "role",defaultValue = "0") int role,
            @RequestParam(value = "account",defaultValue = "") String userName
    ){
        MySession      session =MySession.getInstance();
        RegisterUserVO result  =userService.addUserByManager(session, role, userName);
        return ServerResponse.create(result);
    }

    /**
     * 用户通过注册邀请码进行注册
     * @return
     */
    @ResponseBody
    @RequestMapping("/registerByCode")
    public ServerResponse registerByCode(String registerCode){
        MySession session=MySession.getInstance();
        UserInfoVO result=userService.registerByCode(session,registerCode);
        return ServerResponse.create(result);
    }

    /**
     * 编辑已有用户信息，可编辑的内容仅限：角色、用户名
     * @return
     */
    @ResponseBody
    @RequestMapping("/editUser")
    public ServerResponse editUser(String registerCode,
            @RequestParam(value = "userName",defaultValue = "") String userName,
            @RequestParam(value = "role",defaultValue = "0") int role){
        MySession session=MySession.getInstance();
        RegisterUserVO result=userService.editUser(session,registerCode,userName,role);
        return ServerResponse.create(result);
    }

    /**
     * 删除用户，含有未结束订单的用户无法删除；
     * 用户删除后，如果有上架商品，全部自动下架；
     * @return
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public ServerResponse delUser(String registerCode,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize){
        MySession session=MySession.getInstance();
        PageBean<RegisterUserVO> result=userService.delUser(session,registerCode,pageNum,pageSize);
        return ServerResponse.create(result);
    }

    /**
     * 获取用户列表，暂不支持搜索，有时间了再加
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/userList")
    public ServerResponse getUserList(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "7") int pageSize
    ){
        MySession session=MySession.getInstance();
        PageBean<RegisterUserVO> result=userService.getUserList(session,pageNum,pageSize);
        return ServerResponse.create(result);
    }

}
