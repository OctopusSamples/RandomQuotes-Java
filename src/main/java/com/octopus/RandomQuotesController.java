package com.octopus;

import com.google.common.collect.ImmutableList;
import com.octopus.entity.Author;
import com.octopus.repository.AuthorRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@RestController
public class RandomQuotesController {
    private static final Random RANDOM = new Random();

    @Value("${spring.profiles.active:unknown}")
    private String activeProfile;

    @Autowired
    private ServletContext context;

    @Autowired
    private AuthorRepository authorRepository;

    @RequestMapping("/api/quote")
    public String index(final HttpSession httpSession) {

        /*
            A typical REST API should be stateless. Maintaining state makes deployments, scaling and HA far more difficult.
            However we maintain some trivial state here exactly for the purposes of demonstrating these deployment challenges.
            Also note this code may lead to race conditions, as modifications to the session state should be synchronized
            across threads.
         */
        httpSession.setAttribute("quoteCount", ObjectUtils.defaultIfNull((Integer)httpSession.getAttribute("quoteCount"), 0) + 1);

        final List<Author> authors = ImmutableList.copyOf(authorRepository.findAll());

        final int randomIndex = RANDOM.nextInt(authors.size());

        return "{\"quote\": \"" + authors.get(randomIndex).getQuotes().stream().findFirst().map(q -> q.getQuote()).orElse("No quote associated with author") + "\", " +
                "\"author\": \"" + authors.get(randomIndex).getAuthor() + "\", " +
                "\"appVersion\": \"" + getVersion() + "\", " +
                "\"environmentName\": \"" + activeProfile + "\", " +
                "\"quoteCount\": \"" + (Integer)httpSession.getAttribute("quoteCount") + "\" " +
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
