package org.example;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        StudentReader rdr = new StudentReader("students.txt");
        rdr.getClasses().forEach((e) -> System.out.println(e.getKey() + " " + e.getValue()));

        FileMaker fm = new FileMaker("[Весна 2024] Групи.xls", rdr.getClasses(), 18,
                LocalDate.of(2024, 1, 6),
                LocalDate.of(2024, 6, 30));
        fm.makeFile();
        fm.writeSheets();
        fm.closeFile();
    }

}