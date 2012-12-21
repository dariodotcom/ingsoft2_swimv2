package it.polimi.swim.web.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletAction {
	public void runAction(HttpServletRequest req, HttpServletResponse resp);
}