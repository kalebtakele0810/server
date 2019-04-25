package com.aaupush.server;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.aaupush.com.Post;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.PostRequest;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;

public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostServlet() {
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
		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate object saving */
			Post post = new Post();
			post = requestOBJ.getPayload();
			session.save(post);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("VIEW")) {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			/* hibernate retrieving */
			Post post = new Post();
			Integer postId = requestOBJ.getPostId();
			post = (Post) session.get(Post.class, postId);

			//session.getTransaction().commit();
			rsp.setBody(post);
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("DELETE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			Post post = new Post();
			Integer postId = requestOBJ.getPostId();
			post = (Post) session.get(Post.class, postId);
			session.delete(post);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("UPDATE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			Post newPost = requestOBJ.getPayload();
			session.update(newPost);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		}

		response.getWriter().append(js.objectToJsonString(rsp));

	}

}