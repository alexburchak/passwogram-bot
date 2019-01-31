package org.alexburchak.passwogram.generator.passwordrandom;

import org.alexburchak.passwogram.generator.GeneratorNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.client.MockRestServiceServer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.testng.Assert.assertEquals;

/**
 * @author alexburchak
 */
@SpringBootTest(classes = PasswordRandomGenerator.class)
@RestClientTest(PasswordRandomGenerator.class)
public class PasswordRandomGeneratorTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private PasswordRandomGenerator generator;
    @Autowired
    private MockRestServiceServer server;

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        server.reset();
    }

    @Test
    public void testGetName() {
        assertEquals(generator.getName(), GeneratorNames.PASSWORD_RANDOM_GENERATOR);
    }

    @Test
    public void testGenerate() {
        server.expect(requestTo(PasswordRandomGenerator.URI))
                .andRespond(withSuccess("abcdef", MediaType.TEXT_PLAIN));

        assertEquals(generator.generate(null), "abcdef");
    }
}
