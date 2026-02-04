package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {
    PrintWriter log;
    private final List<String> words;
    private Random random;

    public WordleDictionary(PrintWriter log) {
        this.log = log;
        random = new Random();
        words = new ArrayList<>();
    }

    public void addWords(List<String> dictionaryWords) {
        log.println("Загрузка слов для игры");

        for (String word : dictionaryWords) {
            if (word.length() == 5) {
                if (word.contains("ё")) {
                    word.replace("ё", "е");
                }
                words.add(word.toLowerCase());
            }
        }
        log.println("Загрузка завершена");
    }

    public List<String> getWords() {
        return words;
    }

    public boolean containWord(String word) {

        if (words.contains(word)) {
            return  true;
        }

        return false;
    }

    public boolean isWordInDictionary(String userWord) {

        if (words.contains(userWord)) {
            return true;
        }

        return false;
    }

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
