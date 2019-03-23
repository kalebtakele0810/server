package com.aaupush.server;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import com.aaupush.com.User;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.UserRequest;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;
import com.aaupush.util.UserRequest;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestString = "";
		JsonSerializer js = new JsonSerializer();
		Response rsp = new Response();
		rsp.setStatus("False");
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			requestString = (String) request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		}
		System.out.println("---------------------------" + requestString);
		UserRequest requestOBJ = (UserRequest) js.stringToObject(requestString, UserRequest.class);
		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();
			User user=new User();
			user = requestOBJ.getPayload();
			session.save(user);
			session.getTransaction().commit();
			rsp.setStatus("OK");
		}

		response.getWriter().append(js.objectToJsonString(rsp));
	}

}
