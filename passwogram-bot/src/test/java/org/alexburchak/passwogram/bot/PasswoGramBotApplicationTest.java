package org.alexburchak.passwogram.bot;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

/**
 * @author alexburchak
 */
@SpringBootTest(classes = PasswoGramBotApplication.class)
@WebAppConfiguration
public class PasswoGramBotApplicationTest extends AbstractTestNGSpringContextTests {
    @Test
    public void testContextLoads() {
    }
}
