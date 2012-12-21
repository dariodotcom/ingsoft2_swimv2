package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthenticationServlet
 */
public class AuthenticationServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	public AuthenticationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter w = response.getWriter();
		w.println("I am authentication servlet. Fuck off!");
		w.println("context path: " + request.getContextPath());
		w.println("requestURI: " + request.getRequestURI());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
		String identifier = getActionIDentifier();
	}

	private enum Action {

		LOGIN("login"), LOGOUT("logout"), CREATE("create"), ABOUT("about");
		
		private String identifier;

		Action(String identifier) {
			this.identifier = identifier;
		}

		public static Action getByIdentifier(String identifier) {
			for (Action a : Action.values()) {
				if (a.identifier == identifier) {
					return a;
				}
			}
			return null;
		}
	}
}
