package com.aaupush.server;

import com.aaupush.com.Student;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;
import com.aaupush.util.StudentRequest;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Servlet implementation class UserServlet
 */
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
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

		StudentRequest requestOBJ = (StudentRequest) js.stringToObject(requestString, StudentRequest.class);

		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			Student student = new Student();
			Student= requestOBJ.getPayload();
			session.save(student);

			session.getTransaction().commit();
			rsp.setStatus("OK");
		}

		response.getWriter().append(js.objectToJsonString(rsp));
	}

}
