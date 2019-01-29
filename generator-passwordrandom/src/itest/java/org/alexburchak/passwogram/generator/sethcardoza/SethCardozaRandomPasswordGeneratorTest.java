package org.alexburchak.passwogram.generator.sethcardoza;

import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author alexburchak
 */
@SpringBootTest(classes = SethCardozaRandomPasswordGenerator.class)
public class SethCardozaRandomPasswordGeneratorTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private Generator generator;

    @Test
    public void testGenerate() {
        String password = generator.generate(null);
        Assert.assertNotNull(password);
        Assert.assertTrue(password.matches("^[a-zA-Z\\d!@#$%^&*()]{16}$"), password);
    }
}
