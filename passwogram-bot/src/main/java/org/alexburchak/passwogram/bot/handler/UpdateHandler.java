package org.alexburchak.passwogram.bot.handler;

import com.pengrad.telegrambot.model.Update;

/**
 * @author alexburchak
 */
public interface UpdateHandler {
    void handleUpdate(Update update);
}
