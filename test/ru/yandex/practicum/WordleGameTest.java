package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;

public class WordleGameTest {
    private static PrintWriter printWriter;
    private static WordleDictionaryLoader wordleDictionaryLoader;
    private static WordleDictionary wordleDictionary;
    private static WordleGame wordleGame;

    @BeforeAll
    public static void beforeAll() {
        printWriter = new PrintWriter(System.out);
        wordleDictionaryLoader = new WordleDictionaryLoader(printWriter);
        wordleDictionaryLoader.addWords("words_ru.txt");
        wordleDictionary = new WordleDictionary(printWriter);
        wordleDictionary.addWords(wordleDictionaryLoader.getWords());
        wordleGame = new WordleGame(wordleDictionary, printWriter, wordleDictionaryLoader);
    }

    @Test
    public void testReterningSigns() throws WordLengthExseption,
            WordInDictionaryExseption, NotNumberExseption, RussianLanguageExseption, PreviosWordExseption {
        Assertions.assertEquals(wordleGame.wordCheck(wordleGame.getAnswer()), "+++++");
    }

    @Test
    public void testReduceSizeOfList() {
        int size = wordleDictionary.getWords().size();
        wordleGame.cleanArray("комок", "+++++");
        Assertions.assertNotEquals(size, wordleDictionary.getWords().size());
    }

    @Test
    public void testStepsNot0AndBecomeMore() {
        Assertions.assertNotEquals(wordleGame.getSteps(), 0);
        wordleGame.nextStep();
        Assertions.assertEquals(wordleGame.getSteps(), 2);
    }

    @Test
    public void testAnswerNotNull() {
        Assertions.assertNotNull(wordleGame.getAnswer());
    }
}
