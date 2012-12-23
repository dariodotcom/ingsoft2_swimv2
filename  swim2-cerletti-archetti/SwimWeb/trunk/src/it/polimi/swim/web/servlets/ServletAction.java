package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ServletAction is an interface which purpose is to drive an action.
 */
public interface ServletAction {
	/**
	 * This method serves to perform an action after an HTTP request.
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
	public void runAction(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException;
}