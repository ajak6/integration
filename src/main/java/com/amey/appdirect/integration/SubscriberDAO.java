package com.amey.appdirect.integration;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amey.appdirect.dataBeans.SubscriberBean;

@Component
public class SubscriberDAO extends GenericDAO<SubscriberBean> {

	@Autowired
	public SubscriberDAO(ConnectionPool pool) throws DAOException {
		super(SubscriberBean.class, "subscriber", pool);
	}
}
