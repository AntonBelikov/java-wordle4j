package ru.yandex.practicum;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final List<String> dictionaryWords;
    private PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        dictionaryWords = new ArrayList<>();
        this.log = log;
    }

    public void addWords(String file) {
        BufferedReader fileReader;
        log.println("Производится загрузка словаря");

        try (FileReader files = new FileReader(file)) {
            fileReader = new BufferedReader(files);
            while (fileReader.ready()) {
                String word = fileReader.readLine().trim();
                    dictionaryWords.add(word);
            }
        } catch (FileNotFoundException e) {
            log.println("Файл не найден");
            e.printStackTrace();
        } catch (IOException e) {
            log.println("Ошибка загрузки файла");
            e.printStackTrace();
        }
        log.println("Словарь загружен");
    }

    public List<String> getWords() {
        return dictionaryWords;
    }

    public boolean containWord(String word) {

        if (dictionaryWords.contains(word)) {
            return true;
        }

        return false;
    }
}
