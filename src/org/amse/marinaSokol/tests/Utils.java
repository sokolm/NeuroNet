package org.amse.marinaSokol.tests;

import java.io.*;

import junit.framework.TestCase;

public class Utils extends TestCase{
    static public void compare(String test, String right) {
        try {
            StringReader sr = new StringReader(test);
            FileReader fr =
                new FileReader(right);
            int a, b;
            //int i = 0;
            while (((a = sr.read()) >= 0) && ((b = fr.read()) >= 0)){
                //i++;
                assertEquals(a, b);
            }
            sr.close();
            fr.close();
        } catch(Exception e) {
            throw new RuntimeException();
        }
    }

    static public void writeResult(String text, String fileName) {
        Writer write;
        try {
            write = new FileWriter(fileName);
            write.write(text);
            write.close();
        }
        catch (FileNotFoundException e) {
             System.out.print("не найти файл");
        }
        catch (IOException e) {
             System.out.print("какая-то ошибка");
        }
    }

    static public void compareFile(String testFile, String rightFile) {
        try {
            FileReader frTest = new FileReader(testFile);
            FileReader frRight =
                new FileReader(rightFile);
            int a, b;
            //int i = 0;
            while (((a = frRight.read()) >= 0) && ((b = frTest.read()) >= 0)){
                //i++;
                assertEquals(a, b);
            }
            frRight.close();
            frTest.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }
}
