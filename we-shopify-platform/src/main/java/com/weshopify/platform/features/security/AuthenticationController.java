package com.weshopify.platform.features.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthenticationController {

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String authenticate(HttpServletRequest request) {
		String userName = request.getParameter("username");
		log.info("user name is:\t"+userName);
		String password = request.getParameter("password");
		log.info("password is:\t"+password);
		boolean isLogin = StringUtils.hasText(userName) && StringUtils.hasText(password);
		log.info("does user login success?:\t"+isLogin);
		if(isLogin) {
			return "dashboard";
		}else {
			return "home";
		}
		
	}
}
