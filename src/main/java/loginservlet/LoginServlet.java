package loginservlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import database.DaoManager;
import controler.Orowan_Controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("Todo App");
    
    public LoginServlet() {
        super();
		DaoManager.getInstance();
		Orowan_Controler.getInstance();

    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

	protected String doAction(HttpServletRequest request) throws FileNotFoundException {

		String  vueFinale = "accueil.jsp";

		String action = request.getParameter("action");

		// Action addTodo
		if ("Connexion".equals(action)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(DaoManager.getInstance().checkUserInformations(username, password)) {
				vueFinale = "data.jsp";
			}else {
				vueFinale = "erreur_saisie.jsp";
			}
		}
		
		// Action Import
		if ("Import".equals(action)) {
			String inputFileName = request.getParameter("file");
			String separator = request.getParameter("separator");
			String outputFileName = Orowan_Controler.getInstance().getMainPath()+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Model\\input_"+inputFileName;
			String[] columnNames = {"Cas", "He", "Hs", "Te", "Ts", "Diam_WR", "WRyoung", "offset", "mu_ini", "Force", "G"};
			List<String[]> table;
			
			if("\\t".equals(separator)) {
					table = Orowan_Controler.getInstance().readTableFromFileTab(inputFileName);

			}else {
					table = Orowan_Controler.getInstance().readTableFromFileSemicolumn(inputFileName);

			}
			List<String[]> extractedTable = Orowan_Controler.getInstance().extractColumns(table, 4, 5, 6, 7, 10, 12, 17, 15, 8, 9);
			Orowan_Controler.getInstance().writeTableToFile(outputFileName, extractedTable, columnNames);
			
			Orowan_Controler.getInstance().OrowanExe(inputFileName);
			
			List<Double> values = Orowan_Controler.getInstance().readOutputFile(inputFileName, 3);
			Orowan_Controler.getInstance().plotGraph(values, inputFileName);

			
			vueFinale = "data.jsp";
		}
		
		return vueFinale;
	}

}
