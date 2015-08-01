package com.amey.appdirect.integration;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.amey.appdirect.dataBeans.UserBean;

@Component

public class UserDAO extends GenericDAO<UserBean> {

	@Autowired
	public UserDAO(ConnectionPool pool) throws DAOException {

		super(UserBean.class, "user", pool);
		System.out.println("creating userdao object");
	}

}
