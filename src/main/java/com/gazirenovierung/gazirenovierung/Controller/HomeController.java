package com.gazirenovierung.gazirenovierung.Controller;

import com.gazirenovierung.gazirenovierung.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * HomeController for GAZI RENOVIERUNG.
 * This handles the main routing for the website.
 */
@Controller
public class HomeController {

    @Autowired
    private EmailService emailService;

    /**
     * Maps the root URL (/) to the index.html template.
     * @return The name of the Thymeleaf template (index).
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Maps the /portfolio URL to the portfolio.html template.
     * @return The name of the Thymeleaf template (portfolio).
     */
    @GetMapping("/portfolio")
    public String portfolio() {
        return "portfolio";
    }

    /**
     * Maps the /aboutus URL to the aboutus.html template.
     * @return The name of the Thymeleaf template (aboutus).
     */
    @GetMapping("/aboutus")
    public String aboutUs() {
        return "aboutus";
    }

    /**
     * Handles the contact form submission by calling the EmailService.
     */
    @PostMapping("/contact")
    public String handleContact(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String message,
                                RedirectAttributes redirectAttributes) {

        // KJO DO TË DALË TE RENDER LOGS NËSE KËRKESA ARRIN
        System.out.println("--- KËRKESË E RE PËR EMAIL ---");
        System.out.println("Emri: " + name);
        System.out.println("Email: " + email);

        try {
            emailService.sendContactEmail(name, email, message);
            System.out.println("SUCCESS: Emaili u dërgua nga Service!");
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
        } catch (Exception e) {
            System.out.println("ERROR: Dështoi dërgimi! Arsyeja: " + e.getMessage());
            e.printStackTrace(); // Kjo nxjerr krejt gabimin teknik te logs
            redirectAttributes.addFlashAttribute("error", "Failed to send message: " + e.getMessage());
        }

        // Për Render, kur përdorim JavaScript, kjo është më e sigurt
        return "index";
    }
}