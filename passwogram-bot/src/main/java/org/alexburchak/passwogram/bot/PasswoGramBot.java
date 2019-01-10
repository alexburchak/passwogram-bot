package org.alexburchak.passwogram.bot;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author alexburchak
 */
@SpringBootApplication
@ComponentScan(basePackages = "org.alexburchak.passwogram")
@EnableConfigurationProperties
public class PasswoGramBot {
    /**
     * When running with {@code java -jar passwogram-bot.jar}, specify {@code --spring.config.location} option pointing to the YAML/Properties configuration file
     */
    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder())
                .addCommandLineProperties(true)
                .run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(PasswoGramBot.class)
                .bannerMode(Banner.Mode.LOG);
    }
}
