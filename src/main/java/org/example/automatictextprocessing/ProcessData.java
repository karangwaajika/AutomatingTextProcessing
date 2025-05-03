package org.example.automatictextprocessing;

public class ProcessData {
    public static void main(String[] args) {
        /*
         * DEALING WITH PROCESSING DATA FROM A TEXT
         */

        // ##### Search a string from a text ######
        System.out.println("##### Search a string from a text ######");
        ProcessText textProcessor = new ProcessText();
        String text = "The cat sat on the cathedral and caught a caterpillar\n" +
                "I have many things to do with my cat";
        String result = textProcessor.searchText(text, "\\bcat\\b");
        System.out.println(result);

        // ##### Replace a string ######
        System.out.println("##### Replace a string ######");
        String text1 = "078 298 3266";
        System.out.println(textProcessor.replaceText(text, "\\bcat\\b", "dog"));
        System.out.println(textProcessor.replaceText(text1, "\\s", "-"));

    }
}
