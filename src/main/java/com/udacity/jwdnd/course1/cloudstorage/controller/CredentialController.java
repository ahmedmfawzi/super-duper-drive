package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential")
    public String createCredential(Authentication authentication, @ModelAttribute("credential") Credential credential) {

        User user = userService.getUser(authentication.getName());

        credential.setKey("tempKey");

        credentialService.createCredential(new Credential(null, credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), user.getUserId()));

        HomeController.selectedTab = "credentials";
        HomeController.successMessage = "Credential was successfully added!";

        return "redirect:/home";
    }

    @RequestMapping(value ="/credential", method = { RequestMethod.PATCH , RequestMethod.GET })
    public String updateCredential(@ModelAttribute("credential") Credential credential) {

        credentialService.updateCredential(credential);

        HomeController.selectedTab = "credentials";
        HomeController.successMessage = "Credential was successfully updated!";

        return "redirect:/home";
    }

    @RequestMapping(value ="/credential", method = RequestMethod.DELETE )
    public String deleteCredential(@ModelAttribute("credential") Credential credential) {

        credentialService.deleteCredential(credential);

        HomeController.selectedTab = "credentials";
        HomeController.successMessage = "Credential was successfully deleted!";

        return "redirect:/home";
    }

}
