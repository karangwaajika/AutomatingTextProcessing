package org.example.automatictextprocessing;

import org.example.automatictextprocessing.exceptions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessText {

    public String searchText(String text, String regex) {
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(text);
        String result = "";
        int count = 0;
        while (mat.find()) {
            result += String.format("* Found \"%s\" at index: %d - %d\n", mat.group(), mat.start(), mat.end());
            count++;
        }
        result += "\nTotal Count: " + count;

        return result;

    }

    public String searchTextFromInput(String text, String regex) throws IOException {

        BufferedReader reader = new BufferedReader(new StringReader(text));

        String line;
        String formatedData = "";

        while ((line = reader.readLine()) != null) {
            Pattern pat = Pattern.compile(regex);
            Matcher mat = pat.matcher(line);
            if(mat.find()){
                formatedData += line+"\n";

            }
        }
        reader.close();

        return formatedData;

    }

    public String searchTextFromFile(String path, String regex) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(path));

        String line;
        String formatedData = "";

        while ((line = reader.readLine()) != null) {
            Pattern pat = Pattern.compile(regex);
            Matcher mat = pat.matcher(line);
            if(mat.find()){
                formatedData += line+"\n";

            }
        }
        reader.close();

        return formatedData;

    }

    public String replaceText(String text, String replace, String by) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(text));

        String line;
        String formatedData = "";

        while ((line = reader.readLine()) != null) {
            Pattern pat = Pattern.compile(replace);
            Matcher mat = pat.matcher(line);
            formatedData += mat.replaceAll(by)+"\n";
        }
        reader.close();

        return formatedData;

    }

    public String replaceFileText(String path, String replace, String by) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        String formatedData = "";

        while ((line = reader.readLine()) != null) {
            Pattern pat = Pattern.compile(replace);
            Matcher mat = pat.matcher(line);
            formatedData += mat.replaceAll(by)+"\n";
        }
        reader.close();

        return formatedData;

    }

}
