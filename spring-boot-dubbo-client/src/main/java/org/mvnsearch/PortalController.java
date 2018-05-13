package org.mvnsearch;

import org.mvnsearch.uic.UicTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * portal controller
 *
 * @author linux_china
 */
@Controller
public class PortalController  {
    private final UicTemplate uicTemplate;

    @Autowired
    public PortalController(UicTemplate uicTemplate) {this.uicTemplate = uicTemplate;}

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("user", uicTemplate.findById(1L));
        return "index";
    }
}
