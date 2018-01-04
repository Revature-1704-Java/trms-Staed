import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends Servlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String pass  = request.getParameter("pass");

        JsonObject obj = interpret.login(email, pass);
        if (obj.get("success").getAsBoolean()) {
            session.setAttribute("valid", "true");
        } else {
            session.setAttribute("valid", "false");
        }

        response.getWriter().append(gson.toJson(obj));
	}

}