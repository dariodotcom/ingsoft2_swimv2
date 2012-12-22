package it.polimi.swim.web.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailValidator extends SwimServlet {

	private static final long serialVersionUID = 3350469906837984940L;

	public MailValidator() {
		super();

		setSectionName("validatemail");

		registerGetActionMapping("", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException {
				showValidationPage(req, resp);
			}
		});
	}

	private void showValidationPage(HttpServletRequest req,
			HttpServletResponse resp) {
		
	}
}
