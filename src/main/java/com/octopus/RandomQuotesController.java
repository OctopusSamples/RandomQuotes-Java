package com.octopus;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

@RestController
public class RandomQuotesController {
    private static final Random RANDOM = new Random();
    private static final String LINE_END_RE = "\r\n?|\n";

    @Value("${spring.profiles.active:unknown}")
    private String activeProfile;

    @Autowired
    private ServletContext context;

    @RequestMapping("/api/quote")
    public String index() throws IOException {

        final String[] authors = Resources.toString(Resources.getResource("data/authors.txt"), Charsets.UTF_8).split(LINE_END_RE);
        final String[] quotes = Resources.toString(Resources.getResource("data/quotes.txt"), Charsets.UTF_8).split(LINE_END_RE);
        final int randomIndex = RANDOM.nextInt(authors.length);

        return "{\"quote\": \"" + quotes[randomIndex] + "\", " +
                "\"author\": \"" + authors[randomIndex] + "\", " +
                "\"appVersion\": \"" + getVersion() + "\", " +
                "\"environmentName\": \"" + activeProfile + "\" " +
                "}";
    }

    private String getVersion() {
        try
        {
            final InputStream resourceAsStream = ObjectUtils.defaultIfNull(
                    this.getClass().getResourceAsStream("/META-INF/maven/com.octopus/randomquotes/pom.properties"),
                    context.getResourceAsStream("/META-INF/maven/com.octopus/randomquotes/pom.properties"));
            final Properties props = new Properties();
            props.load( resourceAsStream );
            return props.get("version").toString();
        } catch (final Exception e) {
            return "unknown";
        }

    }
}
