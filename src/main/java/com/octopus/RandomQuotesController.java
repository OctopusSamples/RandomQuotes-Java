package com.octopus;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.octopus.entity.Author;
import com.octopus.repository.AuthorRepository;
import com.octopus.repository.QuoteRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.AuthProvider;
import java.util.List;
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

    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping("/api/quote")
    public String index() throws IOException {

        final List<Author> authors = ImmutableList.copyOf(authorRepository.findAll());

        final int randomIndex = RANDOM.nextInt(authors.size());

        return "{\"quote\": \"" + authors.get(randomIndex).getQuotes().stream().findFirst().map(q -> q.getQuote()).orElse("No quote associated with author") + "\", " +
                "\"author\": \"" + authors.get(randomIndex).getAuthor() + "\", " +
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
