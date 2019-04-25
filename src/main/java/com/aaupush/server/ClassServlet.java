package com.aaupush.server;

import com.aaupush.com.Course;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.ClassRequest;
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

public class ClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassServlet() {
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
		ClassRequest requestOBJ = (ClassRequest) js.stringToObject(requestString, ClassRequest.class);
		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate object saving */
			Course course = new Course();
			course = requestOBJ.getPayload();
			session.save(course);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("DELETE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			Course course = new Course();
			Integer courseId = requestOBJ.getCourseId();
			course = (Course) session.get(Course.class, courseId);
			session.delete(course);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		}

		response.getWriter().append(js.objectToJsonString(rsp));

	}

}