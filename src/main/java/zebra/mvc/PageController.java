package zebra.mvc;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.result.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;

@Deprecated
public class PageController extends StrutsResultSupport {
	@SuppressWarnings("unused")
	private Logger logger = LogManager.getLogger(this.getClass());

	@SuppressWarnings("unused")
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		RequestDispatcher dispatcher = request.getRequestDispatcher(finalLocation);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"), true);

		out.println("<html>");
		out.println("<head>");
		out.println("<script language=\"JavaScript\" src=\"\"></script>");
		out.println("<script language=\"JavaScript\">\n");
		out.println("	function form_submit()\n");
		out.println("	{\n");

		out.println("		history.go(-1);\n");

		out.println("	}\n");
		out.println("\n");
		out.println("</script>\n");
		out.println("</head>");

		out.println("<body onLoad=\"alert('adgadgf');form_submit();\">");

		out.println("</body>");
		out.println("</html>");
		out.flush();
	}
}