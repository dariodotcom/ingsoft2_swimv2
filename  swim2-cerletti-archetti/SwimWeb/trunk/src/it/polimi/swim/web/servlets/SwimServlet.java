package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.UserType;
import it.polimi.swim.web.pagesupport.ErrorType;
import it.polimi.swim.web.pagesupport.Misc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Hashtable;

/**
 * SwimServlet is an abstract class which provides a framework to easily
 * implement all other servlets of the Swim platform.
 */
public abstract class SwimServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * Mapping between the name of a functionality and the object which executes
	 * it in the subclass
	 */
	private Map<String, ServletAction> getActionMappings = new HashMap<String, ServletAction>();
	private Map<String, ServletAction> postActionMappings = new HashMap<String, ServletAction>();
	private String sectionName;

	/**
	 * Default constructor.
	 */
	public SwimServlet() {
		super();
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (getActionMappings == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
		}

		System.out.println("[SWIM] GET dispatcher started.");

		String identifier = getActionIdentifier(req);
		if (getActionMappings.containsKey(identifier)) {
			/* The action identifier has been mapped to an action to perform */
			getActionMappings.get(identifier).runAction(req, resp);
		} else {
			System.out.println(String.format(
					"[SWIM] No GET mapping for action %s in context %s.",
					identifier, sectionName));
			sendError(req, resp, ErrorType.BAD_REQUEST);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (postActionMappings == null) {
			sendError(req, resp, ErrorType.BAD_REQUEST);
		}

		System.out.println("[SWIM] POST dispatcher started.");

		String identifier = getActionIdentifier(req);
		if (postActionMappings.containsKey(identifier)) {
			/* The action identifier has been mapped to an action to perform */
			postActionMappings.get(identifier).runAction(req, resp);
		} else {
			System.out.println(String.format(
					"[SWIM] No POST mapping for action %s in context %s.",
					identifier, sectionName));
			sendError(req, resp, ErrorType.BAD_REQUEST);
		}
	}

	/**
	 * Setter method implemented in order to provide a name to the section (i.e.
	 * context of the request).
	 * 
	 * @param name
	 *            the name of the section.
	 */
	protected void setSectionName(String name) {
		this.sectionName = name;
	}

	/**
	 * This method appends a getActionMapping to the existing set.
	 * 
	 * @param identifier
	 *            a String which is the name of the get action that has been
	 *            added to the set.
	 * @param action
	 *            a ServletAction which is the object that executes the get
	 *            action which has been added to the set.
	 */
	protected void registerGetActionMapping(String identifier,
			ServletAction action) {
		this.getActionMappings.put(identifier, action);
	}

	/**
	 * This method appends a postActionMapping to the existing set.
	 * 
	 * @param identifier
	 *            a String which is the name of the post action that has been
	 *            added to the set.
	 * @param action
	 *            a ServletAction which is the object that executes the post
	 *            action which has been added to the set.
	 */
	protected void registerPostActionMapping(String identifier,
			ServletAction action) {
		this.postActionMappings.put(identifier, action);
	}

	/* Helpers */
	private String getActionIdentifier(HttpServletRequest request) {
		String prefix = request.getContextPath() + "/"
				+ (sectionName.equals("") ? "" : sectionName + "/");
		return request.getRequestURI().replace(prefix, "");
	}

	/**
	 * This method is useful to send error after bad HTTP requests.
	 * 
	 * @param req
	 *            an HttpServletRequest which is the HTTP request.
	 * @param resp
	 *            an HttpServletRequest which is the response to the HTTP
	 *            request.
	 * @throws IOException
	 *             an exception generated because of a bad input.
	 * @throws ServletException
	 *             an exception a servlet can throw when it encounters
	 *             difficulty.
	 */
	protected void sendError(HttpServletRequest req, HttpServletResponse resp,
			ErrorType err) throws IOException, ServletException {

		req.setAttribute("errorType", err);
		req.getRequestDispatcher("/error.jsp").forward(req, resp);
	}

	protected <T> T lookupBean(Class<T> beanClass, String beanName) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		env.put(Context.PROVIDER_URL, "localhost:1099");

		try {
			InitialContext jndiContext = new InitialContext(env);
			return beanClass.cast(jndiContext.lookup(beanName));
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isCustomerLoggedIn(HttpSession session) {
		return isUserTypeLoggedIn(session, UserType.CUSTOMER);
	}

	public static boolean isAdministratorLoggedIn(HttpSession session) {
		return isUserTypeLoggedIn(session, UserType.ADMINISTRATOR);
	}

	private static boolean isUserTypeLoggedIn(HttpSession session, UserType type) {
		if (!isUserLoggedIn(session)) {
			return false;
		}

		Object loggedUser = session.getAttribute(Misc.LOGGED_USERTYPE);
		return loggedUser != null && ((UserType) loggedUser).equals(type);
	}

	public static Boolean isUserLoggedIn(HttpSession session) {
		Object logged = session.getAttribute(Misc.LOGGED_ATTRIBUTE);
		return logged != null && (Boolean) logged;
	}

	public static String getUsername(HttpSession session) {
		Object o = session.getAttribute(Misc.LOGGED_USERNAME);
		return (String) o;
	}

	public static void redirectToRightHome(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		String context = req.getContextPath();

		if (isCustomerLoggedIn(session)) {
			resp.sendRedirect(context + "/home/");
		} else if (isAdministratorLoggedIn(session)) {
			resp.sendRedirect(context + "/admin/");
		} else {
			resp.sendRedirect(context + "/");
		}
	}

}