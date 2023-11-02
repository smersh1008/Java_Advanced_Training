package com.epam.trainning.controller;

import com.epam.trainning.model.SecretInfo;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class SecretController {
    private Map<String, SecretInfo> secretStore;

    @GetMapping("secret")
    public String info() {
        return "secret";
    }

    @PostMapping("generate-link")
    public String generateLink(@RequestParam String secret, Model model) {
        final var uniqueLink = UUID.randomUUID().toString();

        secretStore.put(uniqueLink, new SecretInfo(secret, System.currentTimeMillis()));

        model.addAttribute("uniqueLink", "/secret/" + uniqueLink);
        return "unique_link";
    }

    @GetMapping("secret/{uniqueLink}")
    public String accessSecret(@PathVariable String uniqueLink, Model model) {
        if (secretStore.containsKey(uniqueLink)) {
            final var secretInfo = secretStore.get(uniqueLink);
            model.addAttribute("secret", secretInfo.getSecret());
            secretStore.remove(uniqueLink);
            return "access_secret";
        }
        return "invalid_link";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @Scheduled(fixedRate = 5000) // Run every 5 seconds to clean up secrets that are older than 30 secs
    public void cleanUpExpiredSecrets() {
        long currentTime = System.currentTimeMillis();
        secretStore.entrySet().removeIf(entry -> currentTime - entry.getValue().getCreationTime() > 30000);
    }
}