package pl.gda.zsl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorldWeb
 */
@WebServlet("/HelloWorldWeb")
public class HelloWorldWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HashMap<String, String> dictionary = new HashMap<String, String>();
	
	private GameLocal gameBean;
	
	@EJB (name="GameLocal")
	public void setGameBean(GameLocal gameBean) {
		this.gameBean = gameBean;
	}
	
	
	
	

    /**
     * Default constructor. 
     */
    public HelloWorldWeb() {
    	dictionary.put("apple", "jab³ko");
    	dictionary.put("banana", "banan");
    	dictionary.put("orange", "pomarañcza");
    	dictionary.put("grape", "winogrono");
    	dictionary.put("cherry", "wiœnia");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//out.append("Served at: ").append(request.getContextPath());
		
		out.append("<meta charset=\"UTF-8\">");
		out.append("<h1>Hello World!</h1>" + "<br/>" + "Welcome to the page!" + "<br/>");
		String word = request.getParameter("word");
		out.append("English: " + word + "<br/>");
		out.append("Polish: " + dictionary.get(word) + "<br/>");
		out.append(gameBean.getGraphics(0, false));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
