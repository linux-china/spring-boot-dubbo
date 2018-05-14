package org.mvnsearch;

import org.mvnsearch.uic.AccountManager;
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
public class PortalController {
    private final UicTemplate uicTemplate;
    private final AccountManager accountManager;

    @Autowired
    public PortalController(UicTemplate uicTemplate, AccountManager accountManager) {
        this.uicTemplate = uicTemplate;
        this.accountManager = accountManager;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("user", uicTemplate.findById(1L));
        return "index";
    }
}
