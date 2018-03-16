package org.springframework.samples.mvc;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.samples.mvc.domain.Sample;
import org.springframework.samples.mvc.service.SampleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private SampleService sampleService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping("/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping("/samples")
	public @ResponseBody List<Sample> findAll() {
		return sampleService.findAll();
	}
	
	// win: curl -v -H "Content-Type: application/json" -d {\"name\":\"sam\"} -X POST http://localhost:8080/spring.mvc/sample
	// oth : curl -H "Content-Type: application/json" -X POST -d '{"name":"sam"}' http://localhost:8080/spring.mvc/sample
	@PostMapping("/sample")
	public @ResponseBody Sample sample(@RequestBody Sample sample) {
		return sampleService.save(sample);
	}
	
//	@GetMapping("/sample/{name}")
//	public @ResponseBody Sample sampleName(@PathVariable String name) {
//		Sample sample = new Sample();
//		return sample;
//	}
}
