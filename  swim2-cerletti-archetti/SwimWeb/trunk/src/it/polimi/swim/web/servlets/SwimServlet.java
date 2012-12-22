package it.polimi.swim.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SwimServlet provides a framework to easily implement all other servlets of
 * the swim platform.
 */
public abstract class SwimServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Stuff set by subclasses
	private Map<String, ServletAction> getActionMappings = new HashMap<String, ServletAction>();
	private Map<String, ServletAction> postActionMappings = new HashMap<String, ServletAction>();
	private String sectionName;

	// Default constructor
	public SwimServlet() {
		super();
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (getActionMappings == null) {
			req.setAttribute("error", "No mappings set.");
			sendError(req, resp);
		}

		String identifier = getActionIdentifier(req);
		if (getActionMappings.containsKey(identifier)) {
			// The action identifier has been mapped to an action to perform
			getActionMappings.get(identifier).runAction(req, resp);
		} else {
			req.setAttribute("error", "No mapping for identifier '"
					+ identifier + "'");
			sendError(req, resp);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (postActionMappings == null) {
			sendError(req, resp);
		}

		String identifier = getActionIdentifier(req);
		if (postActionMappings.containsKey(identifier)) {
			// The action identifier has been mapped to an action to perform
			postActionMappings.get(identifier).runAction(req, resp);
		} else {
			sendError(req, resp);
		}
	}

	protected void setSectionName(String name) {
		this.sectionName = name;
	}

	protected void registerGetActionMapping(String identifier,
			ServletAction action) {
		this.getActionMappings.put(identifier, action);
	}

	protected void registerPostActionMapping(String identifier,
			ServletAction action) {
		this.postActionMappings.put(identifier, action);
	}

	/* Helpers */
	private String getActionIdentifier(HttpServletRequest request) {
		String prefix = request.getContextPath() + "/" + sectionName;
		return request.getRequestURI().replace(prefix, "");
	}

	// Stub methods:
	private void sendError(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter w = resp.getWriter();
		w.println("bad request: " + req.getAttribute("error"));
	}
}