package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("log.txt"))) {
            Scanner scanner = new Scanner(System.in);
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
            wordleDictionaryLoader.addWords("words_ru.txt");
            WordleDictionary wordleDictionary = new WordleDictionary(log);
            wordleDictionary.addWords(wordleDictionaryLoader.getWords());
            WordleGame wordleGame = new WordleGame(wordleDictionary, log, wordleDictionaryLoader);

            log.println("Созданы все поля для игры");
            log.println("Загадано слово: " + wordleGame.getAnswer());

            System.out.println("Вас приветствует игра Wordle.");
            System.out.println("Вам нобходимо угадать заагданное слово из 5 букв за 6 ходов.");
            System.out.println("Знаком <-> будут отмечаться буквы, которых нет в слове");
            System.out.println("Знаком <^> будут отмечаться буквы, которые есть в слове, но на другой позиции");
            System.out.println("Знаком <+> будут отмечаться буквы, которые угаданы верно");
            System.out.println("Начинаем игру");

            while (wordleGame.getSteps() <= 6) {
                try {
                    System.out.println("Введите слово из 5 букв");
                    System.out.println("Или нажмите Enter для получения подсказки");
                    String userInput = scanner.nextLine().trim().toLowerCase();
                    log.println("Пользователь ввел слово: " + userInput);

                    if (userInput.equals(wordleGame.getAnswer())) {
                        System.out.println("Поздравляем с угадыванием с 1 раза");
                        log.println("Пользователь угадал с 1 раза");
                        return;
                    }

                    String codeWord = wordleGame.wordCheck(userInput);
                    System.out.println(codeWord);
                    log.println("Результат сравнения со словом" + codeWord);

                    if (codeWord.equals("+++++")) {
                        System.out.printf("Поздравляем!!! Вы угадали слово %s за %d ходов",
                                wordleGame.getAnswer(), wordleGame.getSteps());
                        log.println("Пользователь угадал слово");
                        return;
                    }

                    wordleGame.nextStep();

                } catch (WordLengthExseption e) {
                    e.getMessage();
                } catch (NotNumberExseption e) {
                    e.getMessage();
                } catch (RussianLanguageExseption e) {
                    e.getMessage();
                } catch (WordInDictionaryExseption e) {
                    e.getMessage();
                } catch (PreviosWordExseption e) {
                    e.getMessage();
                }
            }
            System.out.printf("К сожалению, вы не угадали слово %s", wordleGame.getAnswer());
            log.println("Пользователь не угадал слово");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
