package org.alexburchak.passwogram.bot;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

/**
 * @author alexburchak
 */
@SpringBootTest(
        classes = PasswoGramBot.class,
        properties = ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY + "=classpath:/passwogram-bot.yml"
)
@WebAppConfiguration
public class PasswoGramBotTest extends AbstractTestNGSpringContextTests {
    @Test
    public void testContextLoads() {
    }
}
