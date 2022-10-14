import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        File[] listOfFiles = new File[0];
        String path = System.getProperty("user.home") + "\\Downloads\\";
        try {
            File folder = new File(path);
            listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".txt")) {
                    System.out.println(i+1 + ") " + listOfFiles[i].getName());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("no files in downloads");
            exit(0);
        }

        Scanner sc = new Scanner(System.in);

        String filename = "";
        while (!filename.endsWith(".txt")) {
            try {
                System.out.print("Choose File Index: ");
                int fileindex = Integer.parseInt(sc.nextLine()) - 1;
                filename = listOfFiles[fileindex].getName();
                if (!filename.endsWith(".txt")) {
                    throw new NullPointerException();
                }
                System.out.println("File Name: " + filename);
            } catch(NullPointerException e){
                System.out.println("Index out of Bounds");
            } catch(NumberFormatException e) {
                System.out.println("Input not a number");
            }
        }

        List<String> lines = new ArrayList<>();

        try {
            BufferedReader file = new BufferedReader(new FileReader(path + filename));

            String line = file.readLine();

            while (line != null) {
                lines.add(line.trim());
                line = file.readLine();
            }

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("no file");
        } catch (IOException e) {
            System.out.println("read error");
        }

        List<String> output = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("22.150")) {
                int k = i;

                while (!lines.get(k).equals(("Stdout"))) {
                    k--;
                }

                output.add(lines.get(k + 2));
            }
        }

        try {
            Path file = Paths.get("output.txt");
            Files.write(file, output, StandardCharsets.UTF_8);
            System.out.println("success");
        } catch (IOException e) {
            System.out.println("write error");
        }
    }
}