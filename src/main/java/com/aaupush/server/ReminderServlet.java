package com.aaupush.server;

import com.aaupush.com.Reminder;
import com.aaupush.util.ReminderRequest;
import com.aaupush.util.JsonSerializer;
import com.aaupush.util.Response;
import com.aaupush.util.SessionFactoryGenerator;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ReminderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReminderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doReminder(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doReminder(HttpServletRequest request, HttpServletResponse response)
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
		ReminderRequest requestOBJ = (ReminderRequest) js.stringToObject(requestString, ReminderRequest.class);
		if (requestOBJ.getOperation().equalsIgnoreCase("ADD")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate object saving */
			Reminder reminder = new Reminder();
			reminder = requestOBJ.getPayload();
			session.save(reminder);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("VIEW")) {
			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			/* hibernate retrieving */
			Reminder reminder = new Reminder();
			Integer reminderId = requestOBJ.getReminderId();
			reminder = (Reminder) session.get(Reminder.class, reminderId);

			//session.getTransaction().commit();
			rsp.setBody(reminder);
			rsp.setStatus("OK");
			session.close();
		} else if (requestOBJ.getOperation().equalsIgnoreCase("DELETE")) {
			Session session = SessionFactoryGenerator.getSessionFactory().openSession();
			session.beginTransaction();

			/* hibernate retrieving then deleting an object */
			Reminder reminder = new Reminder();
			Integer reminderId = requestOBJ.getReminderId();
			reminder = (Reminder) session.get(Reminder.class, reminderId);
			session.delete(reminder);

			session.getTransaction().commit();
			rsp.setStatus("OK");
			session.close();
		}

		response.getWriter().append(js.objectToJsonString(rsp));

	}

}