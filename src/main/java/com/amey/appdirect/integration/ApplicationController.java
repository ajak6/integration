package com.amey.appdirect.integration;

import org.genericdao.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.amey.appdirect.dataBeans.SubscriberBean;
import com.amey.appdirect.dataBeans.UserBean;

@Controller
public class ApplicationController {

	@Autowired
	UserDAO userDAO;
	@Autowired
	SubscriberDAO subscriberDAO;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/showusers")
	public String users(Model model){
		try {
			UserBean users[]=userDAO.match();
			model.addAttribute("users", users);
			return "showusers";
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/showsubscriber")
	public String subscriber(Model model){
		try {
			SubscriberBean users[]=subscriberDAO.match();
			model.addAttribute("users", users);
			return "showsubscriber";
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return null;
	}
}
