package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VedioController {

	@RequestMapping("/vedio")
	public String greeting() {
		return "vedio1";
	}

	@RequestMapping("/movie/{fileName}/**")
	public ModelAndView vedio(@PathVariable("fileName") String fileName) {
		ModelAndView mv = new ModelAndView("streamView");
		mv.addObject("movieName", fileName);
		return mv;
	}

}
