package com.minjiang.ehouse.controller;


import com.minjiang.ehouse.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Dominick Li
 * @CreateTime 2020/5/8 22:14
 * @description 测试群聊和私聊功能页面
 **/
@Controller
public class TestController {

    @Value("${netty.ws}")
    private String ws;

    @Resource
    UserGroupRepository userGroupRepository;
    /**
     * 登录页面
     */
    @RequestMapping("/login")
    public String login() {
        return "test/login";
    }

    /**
     * 登录后跳转到测试主页
     */
    @PostMapping("/login.do")
    public String login(@RequestParam Integer userId, HttpSession session, Model model) {
        model.addAttribute("ws", ws);
        session.setAttribute("userId", userId);
        model.addAttribute("groupList", userGroupRepository.findGroupIdByUserId(Long.valueOf(userId)));
        return "test/index";
    }
}
