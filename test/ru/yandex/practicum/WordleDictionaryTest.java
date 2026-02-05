package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;

public class WordleDictionaryTest {
    private static PrintWriter printWriter;
    private static WordleDictionaryLoader wordleDictionaryLoader;
    private static WordleDictionary wordleDictionary;

    @BeforeAll
    public static void beforeAll() {
        printWriter = new PrintWriter(System.out);
        wordleDictionaryLoader = new WordleDictionaryLoader(printWriter);
        wordleDictionaryLoader.addWords("words_ru.txt");
        wordleDictionary = new WordleDictionary(printWriter);
        wordleDictionary.addWords(wordleDictionaryLoader.getWords());
    }

    @Test
    public void testMustBeNotEmpty() {
        Assertions.assertFalse(wordleDictionary.getWords().size() == 0);
    }

    @Test
    public void testSize() {
        Assertions.assertEquals(wordleDictionary.getWords().size(), 4159);
    }

    @Test
    public void testGetRandomNotNull() {
        Assertions.assertNotNull(wordleDictionary.getRandomWord());
    }

    @Test
    public void testLengthWord() {
        Assertions.assertEquals(wordleDictionary.getRandomWord().length(), 5);
    }

    @Test
    public void testDifferentRandom() {
        Assertions.assertNotEquals(wordleDictionary.getRandomWord(), wordleDictionary.getRandomWord());
    }

    @Test
    public void testTrueWhenContains() {
        Assertions.assertTrue(wordleDictionary.containWord("яхонт"));
    }

    @Test
    public void testFalseWhenNotContains() {
        Assertions.assertFalse(wordleDictionary.containWord("ящеренок"));
    }

    @Test
    public void testTrueWhenContainsInDictionary() {
        Assertions.assertTrue(wordleDictionary.isWordInDictionary("яхонт"));
    }

    @Test
    public void testFalseWhenNotContainsInDictionary() {
        Assertions.assertFalse(wordleDictionary.isWordInDictionary("ааааа"));
    }
}
