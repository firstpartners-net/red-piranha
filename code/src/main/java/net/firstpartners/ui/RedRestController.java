package net.firstpartners.ui;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that Spring will delegate most Rest requests to in order to decide what to do next
 * @return
 */
@RestController
@EnableAutoConfiguration
public class RedRestController {

    @RequestMapping(value = "api/some-method", method = RequestMethod.GET)
    public String index(Model model) {
        
        return "hellow world";
    }
}