package loginservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DaoManager;

import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("Todo App");
    
    public LoginServlet() {
        super();
		DaoManager.getInstance();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doAction(request);

		request.getRequestDispatcher(doAction(request)).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected String doAction(HttpServletRequest request) {

		String vueFinale = "jsp/error.jsp"; // not created yet

		String action = request.getParameter("action");

		// Action1 addTodo
		if ("Connexion".equals(action)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(DaoManager.getInstance().checkUserInformations(username, password)) {
				vueFinale = "jsp/success.jsp";
			}

		}
		return vueFinale;
	}
}
