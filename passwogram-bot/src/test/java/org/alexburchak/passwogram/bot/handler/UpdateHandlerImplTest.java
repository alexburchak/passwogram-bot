package org.alexburchak.passwogram.bot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.alexburchak.passwogram.generator.GeneratorFactory;
import org.alexburchak.passwogram.generator.api.Generator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * @author alexburchak
 */
public class UpdateHandlerImplTest {
    @Spy
    private TelegramBot telegramBot = new TelegramBot("123");
    @Mock
    private GeneratorFactory generatorFactory;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private UpdateHandlerImpl updateHandler = new UpdateHandlerImpl();

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        doReturn(Collections.singletonList(new Generator() {
            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public String generate(String sample) {
                return "abcdef";
            }
        })).when(generatorFactory).getGenerators();

        doReturn(message).when(update).message();
        doReturn(chat).when(message).chat();
        doReturn(1L).when(chat).id();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        reset(telegramBot, generatorFactory, update, message, chat);
    }

    @Test
    public void testHandleUpdateSuccess() {
        doAnswer(i -> {
            SendMessage message = (SendMessage) i.getArguments()[0];
            assertNotNull(message);
            return null;
        }).when(telegramBot).execute(any(BaseRequest.class));

        updateHandler.handleUpdate(update);

        verify(telegramBot).execute(any(BaseRequest.class));
        verify(generatorFactory).getGenerators();
        verifyNoMoreInteractions(telegramBot, generatorFactory);
    }

    @Test
    public void testHandleUpdateFailure() {
        doThrow(new RuntimeException()).when(telegramBot).execute(any(BaseRequest.class));

        updateHandler.handleUpdate(update);

        verify(telegramBot).execute(any(BaseRequest.class));
        verify(generatorFactory).getGenerators();
        verifyNoMoreInteractions(telegramBot, generatorFactory);
    }
}
