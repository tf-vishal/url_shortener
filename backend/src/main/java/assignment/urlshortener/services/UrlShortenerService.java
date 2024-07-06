package assignment.urlshortener.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {
    private static final String BASE_URL = "http://localhost:8080/";
    private static final String REDIS_KEY_PREFIX = "shortened_urls:";
    private static final String DOMAIN_COUNTS_KEY = "domain_counts";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String shortenUrl(String originalUrl) {
        // Check if the shortened URL already exists in Redis
        String shortenedUrl = redisTemplate.opsForValue().get(originalUrl);
        if (shortenedUrl != null) {
            return shortenedUrl;
        }

        // Generate a unique identifier for the original URL
        String uniqueIdentifier = generateUniqueIdentifier(originalUrl);

        // Create the shortened URL
        shortenedUrl = BASE_URL + uniqueIdentifier;
        // Store the URL mapping in Redis
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + uniqueIdentifier, originalUrl);
        redisTemplate.opsForValue().set(originalUrl, shortenedUrl);
        redisTemplate.expire(REDIS_KEY_PREFIX + uniqueIdentifier, 30, TimeUnit.DAYS); // Set expiration time
        
        String domain = extractDomain(originalUrl);
        incrementDomainCount(domain);

        return shortenedUrl;
    }

    public String getOriginalUrl(String uniqueIdentifier) {
        return redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + uniqueIdentifier);
    }

    private String generateUniqueIdentifier(String originalUrl) {
         int hash = originalUrl.hashCode();
        return Integer.toString(Math.abs(hash), 36);
    }
    public Map<String, Integer> getTopDomains(int topCount) {
        // Get the top N domains by count
        Map<String, Integer> topDomains = new HashMap<>();
        Set<Object> keys = redisTemplate.opsForHash().keys(DOMAIN_COUNTS_KEY);
    
        Set<String> domainKeys = keys.stream()
                .map(Object::toString)  
                .collect(Collectors.toSet());
    
        if (domainKeys != null) {
            for (String domain : domainKeys) {
                Integer count = Integer.parseInt(redisTemplate.opsForHash().get(DOMAIN_COUNTS_KEY, domain).toString());
                topDomains.put(domain, count);
            }
        }
    
        return topDomains.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(topCount)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    

    private void incrementDomainCount(String domain) {
        redisTemplate.opsForHash().increment(DOMAIN_COUNTS_KEY, domain, 1);
    }

    private String extractDomain(String url) {
        try {

            if(!url.startsWith("http://")&& !url.startsWith("https://")){
                url="http://"+ url;
            }
            URI uri = new URI(url);
            String domain = uri.getHost();

            if (domain != null && domain.startsWith("www.")) {
                domain = domain.substring(4);
            }

            return domain;
        } catch (URISyntaxException e) {
            return null;
        }
    }
}

