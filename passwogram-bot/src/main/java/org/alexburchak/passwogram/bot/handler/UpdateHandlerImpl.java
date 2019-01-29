package org.alexburchak.passwogram.bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.alexburchak.passwogram.generator.GeneratorFactory;
import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author alexburchak
 */
@Slf4j
@Component
public class UpdateHandlerImpl implements UpdateHandler {
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private GeneratorFactory generatorFactory;

    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();

        Long chatId = message.chat().id();
        log.debug("Notification from chat {}", chatId);

        try {
            String sample = message.text();

            List<Generator> generators = new ArrayList<>(generatorFactory.getGenerators());
            Collections.shuffle(generators);

            String password = null;
            for (Generator generator: generators) {
                try {
                    password = generator.generate(sample);
                    log.info("Generated password for chat {} with {}", chatId, generator.getName());
                } catch (Throwable t) {
                    log.error("Failed to generate password for chat {} with {}", chatId, generator.getName(), t);
                }
            }

            SendMessage sendTranslation = new SendMessage(chatId, password != null ? password : "Could not generate one for you, sorry...");
            telegramBot.execute(sendTranslation);
        } catch (Throwable t) {
            log.error("Failed to send password to chat {}", chatId, t);
        }
    }
}
