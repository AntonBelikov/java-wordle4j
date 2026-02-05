package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;

public class WordleDictionaryLoaderTest {
    private static PrintWriter printWriter;
    private static WordleDictionaryLoader wordleDictionaryLoader;

    @BeforeAll
    public static void beforeAll() {
        printWriter = new PrintWriter(System.out);
        wordleDictionaryLoader = new WordleDictionaryLoader(printWriter);
        wordleDictionaryLoader.addWords("words_ru.txt");
    }

    @Test
    public void testMustBeNotEmpty() {
        Assertions.assertFalse(wordleDictionaryLoader.getWords().size() == 0);
    }

    @Test
    public void testSize() {
        Assertions.assertEquals(wordleDictionaryLoader.getWords().size(), 67763);
    }

    @Test
    public void testTrueWhenContains() {
        Assertions.assertTrue(wordleDictionaryLoader.containWord("ящеренок"));
    }

    @Test
    public void testFalseWhenNotContains() {
        Assertions.assertFalse(wordleDictionaryLoader.containWord("ааааааа"));
    }
}
