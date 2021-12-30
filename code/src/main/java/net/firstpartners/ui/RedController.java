package net.firstpartners.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Class that Spring will delegate most web requests to in order to decide what to do next
 * @return
 */
@Controller
public class RedController {

	@GetMapping("/zzz-some-made-up-method")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        // this attribute will be available in the view index.html as a thymeleaf variable
        model.addAttribute("eventName", "FIFA 2018");
        // this just means render index.html from static/ area
        return "index";
    }
}