package hello;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SessionController {

	@RequestMapping("/session")
	@ResponseBody
	public String doCreateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60*30);
		System.out.println("SessionController.doCreateSession()");
		return "hi";
	}
	
	@RequestMapping("/session-inv")
	@ResponseBody
	public String doInvalidateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		System.out.println("SessionController.doInvalidateSession()");
		if(session == null) {
			System.err.println("session is null");
			return "end";
		}
		session.invalidate();
		
		return "end";
	}
	
}
