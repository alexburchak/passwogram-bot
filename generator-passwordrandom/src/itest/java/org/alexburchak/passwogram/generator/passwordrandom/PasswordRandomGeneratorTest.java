package org.alexburchak.passwogram.generator.passwordrandom;

import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author alexburchak
 */
@SpringBootTest(classes = PasswordRandomGenerator.class)
public class PasswordRandomGeneratorTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private Generator generator;

    @Test
    public void testGenerate() {
        String password = generator.generate(null);
        assertNotNull(password);
        assertTrue(password.matches("^.{11}$"), password);
    }
}
