package org.alexburchak.passwogram.generator;

import lombok.Getter;
import lombok.Setter;
import org.alexburchak.passwogram.generator.api.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author alexburchak
 */
@Service
@Setter
@Getter
public class GeneratorFactory {
    @Autowired
    private List<Generator> generators;
}
