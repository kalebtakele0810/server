package com.aaupush.server;

import com.aaupush.com.Forum;
import com.aaupush.util.ForumRequest;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;
import org.hibernate.Session;
import org.hibernate.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForumServlet() {
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
		ForumRequest requestOBJ = (ForumRequest) js.stringToObject(requestString, ForumRequest.class);
		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate object saving */
			Forum forum = new Forum();
			forum = requestOBJ.getPayload();
			session.save(forum);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("SEARCH")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			String name = requestOBJ.getForumName();
			String hql = "FROM Forum F WHERE E.name = :name";
			Query query = session.createQuery(hql);
			query.setparameter("name",name);
			List forums = query.list();

			session.getTransaction().commit();
			rsp.setBody(forums);
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("VIEW")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			Forum forum = new Forum();
			Integer forumId = requestOBJ.getForumId();
			forum = (Forum) session.get(Forum.class, forumId);

			//session.getTransaction().commit();
			rsp.setBody(forum);
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("JOIN")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			Forum forum = new Forum();
			Integer forumId = requestOBJ.getForumId();
			forum = (Forum) session.get(Forum.class, forumId);

			User user = new User();
			Integer userId = requestOBJ.getuserId();
			user = (User) session.get(User.class, userId);

			forum.members.add(user);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("DELETE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			Forum forum = new Forum();
			Integer forumId = requestOBJ.getForumId();
			forum = (Forum) session.get(Forum.class, forumId);
			session.delete(forum);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		}

		response.getWriter().append(js.objectToJsonString(rsp));

	}

}