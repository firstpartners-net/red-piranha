package net.firstpartners.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.firstpartners.ui.utils.Config;


/**
 * Class that Spring will delegate most web (not rest) requests to in order to decide what to do next
 * @return
 */
@Controller
public class RedController {

	//handle for our config
	@Autowired
	Config appConfig;
	
	@Autowired
	private ApplicationContext context;
	
	//Logger if needed
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/zzz-some-made-up-method")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
    	
        // this attribute will be available in the view index.html as a thymeleaf variable
        model.addAttribute("configBean", appConfig);
                
        // this just means render index.html from static/ area
        return "index";
    }
    
    @PostMapping("/updateConfig")
    public String submitForm(Model model, @ModelAttribute("configBean") Config configBean) {
    	
    	//Make sure our Global Config gets updated with incoming values
    	appConfig.setInputFileName(configBean.getInputFileName());
    	appConfig.setRule1(configBean.getRule1());
    	appConfig.setOutputFileName(configBean.getOutputFileName());
    	
    	//update the web values with system value (in case we missed any)
    	model.addAttribute("configBean", appConfig);
    	
    	log.debug("Updated model:"+appConfig);
    	
        return "index";
    }
    	
}