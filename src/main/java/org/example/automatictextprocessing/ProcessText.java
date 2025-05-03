package org.example.automatictextprocessing;

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

    public String replaceText(String text, String replace, String by) {
        Pattern pat = Pattern.compile(replace);
        Matcher mat = pat.matcher(text);

        return mat.replaceAll(by);

    }

}
