package com.aaupush.server;

import com.aaupush.com.Post;
import com.aaupush.com.User;
import com.aaupush.com.Staff;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.PostRequest;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestString = "";
		/*
		 *
		 * Json serializer converts json to java object and vise versa
		 *
		 */
		JsonSerializer js = new JsonSerializer();
		/*
		 * initialize response object,so that it will be filled with response data
		 */
		Response rsp = new Response();
		rsp.setStatus("False");
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			/*
			 *
			 * get request as string
			 *
			 */
			requestString = (String) request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		}
		System.out.println("---------------------------" + requestString);
		/*
		 * converts request string into UserRequest object
		 *
		 */
		PostRequest requestOBJ = (PostRequest) js.stringToObject(requestString, PostRequest.class);
		if (requestOBJ.getOperation().equalsIgnoreCase("CLICK")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			Post post = new Post();
			Integer postId = requestOBJ.getPostId();
			post = (Post) session.get(Post.class, postId);

			User user = new User();
			Integer userId = requestOBJ.getUserId();
			user = (User) session.get(User.class, userId);

			post.readBy.add(user);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("INVITE")) {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Staff staff = new Staff();
			staff = requestOBJ.getStaffPayload();
			session.save(staff);

			//Send Email

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("FORGOT")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			User user = new User();
			Integer userId = requestOBJ.getUserId();
			user = (User) session.get(User.class, userId);

			//Assign random string as the password of the account and send an email

			session.update(user);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("UPDATE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			User newUser = requestOBJ.getUserPayload();
			session.update(newUser);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		}

		response.getWriter().append(js.objectToJsonString(rsp));

	}

}