package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SwimServlet
 */
public class SwimServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Stuff set by subclasses
	protected Map<String, ServletAction> getActionMappings;
	protected Map<String, ServletAction> postActionMappings;
	protected String sectionName;

	// Default constructor
	public SwimServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(getActionMappings == null){
			//Nothing to do here!
		}
		
		String identifier = getActionIdentifier(req);
		if(getActionMappings.containsKey(identifier)){
			getActionMappings.get(identifier).runAction(req, resp);
		}		
		
	}

	private String getActionIdentifier(HttpServletRequest request) {
		String prefix = request.getContextPath() + "/"
				+ (sectionName == null ? "" : sectionName + "/");
		return request.getRequestURI().replace(prefix, "");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
