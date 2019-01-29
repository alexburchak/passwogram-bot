package org.alexburchak.passwogram.generator.api;

/**
 * @author alexburchak
 */
public interface Generator {
    String getName();

    String generate(String sample);
}
