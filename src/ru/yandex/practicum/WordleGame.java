package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private Map<String, Integer> inputWords;
    PrintWriter log;
    private WordleDictionaryLoader dictionaryLoader;

    public WordleGame(WordleDictionary dictionary,PrintWriter log, WordleDictionaryLoader dictionaryLoader) {
        answer = dictionary.getRandomWord();
        steps = 1;
        this.dictionary = dictionary;
        inputWords = new LinkedHashMap<>();
        this.log = log;
        this.dictionaryLoader = dictionaryLoader;
    }

    public String wordCheck(String userWord) throws WordLengthExseption,
            NotNumberExseption, RussianLanguageExseption, PreviosWordExseption, WordInDictionaryExseption {

        if (userWord.matches(".*\\d.*")) {
            System.out.println("Слово должно содержать только буквы");
            log.println("Слово содержит цифры");
            throw new NotNumberExseption("Введены буквы");
        }
        if (inputWords.containsKey(userWord)) {
            System.out.println("Слово было введено ранее");
            log.println("Введено использованное слово");
            throw new PreviosWordExseption("Введено использованное слово");
        }

        if (userWord.isEmpty()) {
            userWord = compHelp();
            log.println("Пользователь запросил подсказку, компьютер выбрал слово: " + userWord);
        }

        if (!userWord.matches(".\\p{IsCyrillic}.*")) {
            System.out.println("Введите слово на русском языке");
            log.println("Слова на другом языке");
            throw new RussianLanguageExseption("Не наш язык");
        }

        if (userWord.length() != 5) {
            System.out.println("Должно состоять из 5 букв");
            log.println("Слово состоит из другого количества букв");
            throw new WordLengthExseption("Длина не 5");
        }

        if (!dictionaryLoader.containWord(userWord)) {
            System.out.println("Слово отсутствует в словаре");
            log.println("Введеное лово отсутствует в словаре");
            throw new WordInDictionaryExseption("Слово отсутствует в словаре");
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < userWord.length(); i++) {

            if (userWord.charAt(i) == answer.charAt(i)) {
                builder.append("+");
            } else if (answer.indexOf(userWord.charAt(i)) != -1) {
                builder.append("^");
            } else {
                builder.append("-");
            }
        }
        log.println("Проверка слова закончена");
        inputWords.put(userWord, 1);
        cleanArray(userWord,builder.toString());
        return  builder.toString();
    }

    public void cleanArray(String userInput, String codeOfAnswer) {
        Iterator<String> wordIterator = dictionary.getWords().iterator();
        log.println("Производится чистка слов");

        for (int i = 0; i < codeOfAnswer.length(); i++) {

            if (codeOfAnswer.charAt(i) == '-') {
                wordIterator = dictionary.getWords().iterator();
                while (wordIterator.hasNext()) {
                    String nextWord = wordIterator.next();

                    if (nextWord.indexOf(userInput.charAt(i)) != -1) {
                        wordIterator.remove();
                    }
                }
            } else if (codeOfAnswer.charAt(i) == '^') {
                wordIterator = dictionary.getWords().iterator();
                while (wordIterator.hasNext()) {
                    String nextWord = wordIterator.next();

                    if (nextWord.indexOf(userInput.charAt(i)) == -1) {
                        wordIterator.remove();
                    }
                }
            } else {
                wordIterator = dictionary.getWords().iterator();
                while (wordIterator.hasNext()) {
                    String nextWord = wordIterator.next();

                    if (nextWord.charAt(i) != userInput.charAt(i)) {
                        wordIterator.remove();
                    }
                }
            }
        }
    }

    public String compHelp() {
        while(true) {
            String helpWord =  dictionary.getRandomWord();

            if (!inputWords.containsKey(helpWord)) {
                System.out.println(helpWord);
                return helpWord;
            }
        }
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public void nextStep() {
        steps++;
    }
}
