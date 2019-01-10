package org.alexburchak.passwogram.bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alexburchak
 */
@Slf4j
@Component
public class UpdateHandlerImpl implements UpdateHandler {
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private Generator generator;

    @Override
    public void handleUpdate(Update update) {
        Message message = update.message();

        Long chatId = message.chat().id();
        log.debug("Notification from chat {}", chatId);

        try {
            String sample = message.text();
            SendMessage sendTranslation = new SendMessage(chatId, generator.generate(sample));
            telegramBot.execute(sendTranslation);

            log.debug("Successfully generated password for chat {}", chatId);
        } catch (Throwable t) {
            log.error("Failed to generate password for chat {}", chatId, t);
        }
    }
}
