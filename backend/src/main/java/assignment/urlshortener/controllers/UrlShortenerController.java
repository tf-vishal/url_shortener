package assignment.urlshortener.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import assignment.urlshortener.services.UrlShortenerService;

@CrossOrigin
@RestController
@RequestMapping("")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody Map<String, String> requestBody) {
        String originalUrl = requestBody.get("urlToShorten");
        // Add a scheme if missing
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }
        return urlShortenerService.shortenUrl(originalUrl);
    }

    @GetMapping("/{shortenedUrl}")
    public Object redirect(@PathVariable String shortenedUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortenedUrl);
        if (originalUrl == null) {
            return "No url found";
        }

        return new RedirectView(originalUrl);
    }

    @GetMapping("/metrics")
    public Map<String, Integer> getMetrics() {
        return urlShortenerService.getTopDomains(3);
    }
}
