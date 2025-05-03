package org.example.automatictextprocessing;

import org.example.automatictextprocessing.exceptions.FileEmptyException;

import java.io.*;

public class ProcessFile {
    public String readFromAFile(String path) throws IOException {

        FileReader fr = null;
        fr = new FileReader(path);
        if (fr.read() == -1) {
            throw new FileEmptyException("The file is empty !!!");
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        String result = "";

        while ((line = reader.readLine()) != null) {
            result += line + "\n";
        }
        fr.close();
        reader.close();
        return result;

    }

    public void writeToFile(String path, String text) throws IOException {

        FileWriter fw = null;
        fw = new FileWriter(path, true);

        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(text);
        writer.newLine();

        writer.close();
        fw.close();

    }
}
