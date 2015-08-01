package com.amey.appdirect.integration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.amey.appdirect.dataBeans.SubscriberBean;
import com.amey.appdirect.dataBeans.UserBean;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@RestController
public class EventController {
	@Autowired
	UserDAO userDAO;

	@Autowired
	SubscriberDAO subscriberDAO;

	@RequestMapping("/cancel")
	public @ResponseBody ReturnResult cancelSubscription(@RequestParam("url") String url)
			throws ParserConfigurationException, SAXException, IOException {
		HttpURLConnection connection = connect(url);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();

		StringBuilder xmlStringBuilder = new StringBuilder();
		Document cancel = builder.parse(connection.getInputStream());

		XPath xPath = XPathFactory.newInstance().newXPath();

		String expression = "/event/creator/email";
		String email;
		try {
			email = xPath.compile(expression).evaluate(cancel);

			try {
				subscriberDAO.delete(email);
				UserBean[] users = userDAO.match(MatchArg.equals("creator", email));
				for (int i = 0; i < users.length; i++) {
					userDAO.delete(users[i].getEmail());
				}
			} catch (RollbackException e) {
				e.printStackTrace();
			}
			// delete all the useraccounts associated with it

		} catch (XPathExpressionException e1) {
			e1.printStackTrace();
		}
		ReturnResult n = new ReturnResult();
		n.setSuccess("true");
		return n;
	}

	@RequestMapping("/assign")
	public @ResponseBody ReturnResult assignUser(@RequestParam("url") String url) {
		HttpURLConnection connection = connect(url);
		UserBean user = assignedUser(connection);
		try {
			userDAO.create(user);

		} catch (RollbackException e) {
			e.printStackTrace();
		}
		ReturnResult n = new ReturnResult();
		n.setMessage("user created");
		n.setSuccess("true");
		return n;
	}

	@RequestMapping("/unassign")
	public @ResponseBody ReturnResult unassignUser(@RequestParam("url") String url) {
		System.out.println("unassign url received " + url);
		HttpURLConnection connection = connect(url);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();

			StringBuilder xmlStringBuilder = new StringBuilder();
			Document unassign = builder.parse(connection.getInputStream());

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "/event/creator/email";
			String creator = xPath.compile(expression).evaluate(unassign);
			expression = "/event/payload/user/email";
			String userEmail = xPath.compile(expression).evaluate(unassign);
			userDAO.delete(userEmail);
			ReturnResult res=new ReturnResult();
			res.setSuccess("true");
			return res;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/subs")
	public @ResponseBody ReturnResult subcription(@RequestParam("url") String url, HttpServletRequest request)
			throws ParserConfigurationException, SAXException {
		// sign this url with acces token and secret and fetch the XML data from
		// Appdirect
		HttpURLConnection connection = connect(url);
		SubscriberBean userDetails = subscriberDetails(connection);
		// save the details in database
		try {
			subscriberDAO.create(userDetails);
			ReturnResult res = new ReturnResult();
			res.setAccountIdentifier(userDetails.getEmail());
			res.setSuccess("true");
			res.setMessage("account created successfully");
			return res;
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		// return error
		return null;
	}

	private HttpURLConnection connect(String url) {
		OAuthConsumer consumer = new DefaultOAuthConsumer("ameytest-34007", "A7Q12Vao2SdLik8N");
		URL returnURL = null;
		try {
			returnURL = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) returnURL.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			connection.setRequestMethod("GET");

			consumer.sign(connection);
			connection.connect();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private UserBean assignedUser(HttpURLConnection connection) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;

			builder = factory.newDocumentBuilder();

			StringBuilder xmlStringBuilder = new StringBuilder();
			Document userAssignment = builder.parse(connection.getInputStream());

			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "/event/creator/email";
			String creatorEmail = xPath.compile(expression).evaluate(userAssignment);
			expression = "/event/payload/user/email";
			String userEmail = xPath.compile(expression).evaluate(userAssignment);

			expression = "/event/payload/account/accountIdentifier";
			String accountIdentifier = xPath.compile(expression).evaluate(userAssignment);
			expression = "/event/payload/user/firstName";

			String firstName = xPath.compile(expression).evaluate(userAssignment);
			expression = "/event/payload/user/openId";
			String openId = xPath.compile(expression).evaluate(userAssignment);
			UserBean user = new UserBean();
			user.setCreator(creatorEmail);
			user.setEmail(userEmail);
			user.setFirstName(firstName);
			user.setOpenId(openId);
			return user;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private SubscriberBean subscriberDetails(HttpURLConnection connection) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringBuilder xmlStringBuilder = new StringBuilder();
			Document subscriptionOrder = builder.parse(connection.getInputStream());

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/event/creator/email";
			String email = xPath.compile(expression).evaluate(subscriptionOrder);
			expression = "/event/creator/firstName";
			String firstName = xPath.compile(expression).evaluate(subscriptionOrder);
			System.out.println("email is " + email + " first name is " + firstName);

			expression = "/event/payload/order/editionCode";
			String edition = xPath.compile(expression).evaluate(subscriptionOrder);
			System.out.println(" edition is " + edition);
			expression = "/event/payload/company/name";

			String company = xPath.compile(expression).evaluate(subscriptionOrder);

			expression = "/event/creator/openId";
			String openID = xPath.compile(expression).evaluate(subscriptionOrder);
			expression = "/event/creator/firstName";

			SubscriberBean userDetails = new SubscriberBean();
			userDetails.setCompany(company);
			userDetails.setEdition(edition);
			userDetails.setEmail(email);
			userDetails.setAccountIdentifier(email);
			userDetails.setFirstname(firstName);
			userDetails.setOpenID(openID);
			return userDetails;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return null;
	}
}
