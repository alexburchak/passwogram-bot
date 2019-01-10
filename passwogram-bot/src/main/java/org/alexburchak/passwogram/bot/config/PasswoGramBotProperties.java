package org.alexburchak.passwogram.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author alexburchak
 */
@Component
@ConfigurationProperties("passwogram-bot")
@Getter
@Setter
public class PasswoGramBotProperties {
    private String apiKey;
}
