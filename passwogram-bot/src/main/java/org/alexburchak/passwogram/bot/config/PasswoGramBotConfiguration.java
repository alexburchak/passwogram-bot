package org.alexburchak.passwogram.bot.config;

import com.pengrad.telegrambot.TelegramBot;
import org.alexburchak.passwogram.bot.handler.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.pengrad.telegrambot.UpdatesListener.CONFIRMED_UPDATES_ALL;

/**
 * @author alexburchak
 */
@Configuration
public class PasswoGramBotConfiguration {
    @Autowired
    private UpdateHandler handler;

    @Bean
    public TelegramBot telegramBot(PasswoGramBotProperties properties) {
        TelegramBot bot = new TelegramBot.Builder(properties.getApiKey())
                .build();
        bot.setUpdatesListener(u -> {
                    u.forEach(handler::handleUpdate);
                    return CONFIRMED_UPDATES_ALL;
                }
        );
        return bot;
    }
}
