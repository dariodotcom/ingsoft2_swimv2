package it.polimi.swim.web.servlets;

import it.polimi.swim.business.bean.remote.UserProfileControllerRemote;
import it.polimi.swim.business.entity.Customer;
import it.polimi.swim.web.pagesupport.Misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CustomerImageServlet
 */
public class CustomerImageServlet extends SwimServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTEXT_NAME = "userphoto";

	public static final String USER_PARAM = "u";
	private final String imageFormat = "png";

	private enum CustomerImage {
		FULL, THUMB
	}

	/**
	 * @see SwimServlet#SwimServlet()
	 */
	public CustomerImageServlet() {
		super();

		setSectionName(CONTEXT_NAME);

		registerGetActionMapping("full.png", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCustomerImage(CustomerImage.FULL, req, resp);
			}
		});

		registerGetActionMapping("thumb.png", new ServletAction() {
			public void runAction(HttpServletRequest req,
					HttpServletResponse resp) throws IOException,
					ServletException {
				showCustomerImage(CustomerImage.THUMB, req, resp);
			}
		});

	}

	private void showCustomerImage(CustomerImage image, HttpServletRequest req,
			HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession();
		BufferedImage userImage = null;
		String username = req.getParameter(USER_PARAM);

		if (Misc.isStringEmpty(username)) {
			// Nothing to show
			return;

		} else {

			// Retrieve image from DB
			UserProfileControllerRemote userCtrl = lookupBean(
					UserProfileControllerRemote.class, Misc.BeanNames.PROFILE);

			Customer c = userCtrl.getByUsername(username);

			if (c == null) {
				// Nothing to show
				return;
			}

			if (image.equals(CustomerImage.FULL)) {
				userImage = c.getCustomerPhoto();
			} else {
				userImage = c.getCustomerThumbnail();
			}

			// If customer has no photo, load default.
			if (userImage == null) {
				String path = image.equals(CustomerImage.FULL) ? Misc.DEFAULT_PHOTO_FULL_URL
						: Misc.DEFAULT_PHOTO_THUMB_URL;
				userImage = loadImage(session, path);
			}
		}

		// Write image to response
		resp.setContentType("image/png");
		ImageIO.write(userImage, imageFormat, resp.getOutputStream());

		return;
	}

	/* Helpers */
	private BufferedImage loadImage(HttpSession session, String path)
			throws IOException {
		ServletContext ctx = session.getServletContext();
		InputStream input = ctx.getResourceAsStream(path);
		return ImageIO.read(input);
	}
}