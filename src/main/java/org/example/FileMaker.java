package org.example;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import org.example.util.CellStyle;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

public class FileMaker {

    public static class Pair<A, B> {
        private A key;
        private B value;

        public Pair(A a, B b) {
            this.key = a;
            this.value = b;
        }

        public A getKey() {
            return key;
        }

        public void setKey(A key) {
            this.key = key;
        }

        public B getValue() {
            return value;
        }

        public void setValue(B value) {
            this.value = value;
        }
    }

    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    private final String filename;
    private final List<Pair<String, List<String>>> groups;
    private WritableWorkbook workbook;
    private int index;
    private final int startLessonNum;
    private final LocalDate startDate;
    private final LocalDate finalDate;

    public FileMaker(String filename, List<Pair<String, List<String>>> groups,
                     int startLessonNum, LocalDate startDate, LocalDate finalDate) {
        this.filename = filename;
        this.groups = groups;
        this.workbook = null;
        this.index = 0;
        this.startLessonNum = startLessonNum;
        this.startDate = startDate;
        this.finalDate = finalDate;
    }

    public void makeFile() {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeSheets() {
        groups.forEach(entry -> {
            try {
                WritableSheet sheet = workbook.createSheet(entry.getKey(), index);
                writeGroupToSheet(sheet, entry.getKey(), entry.getValue());
                this.index++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeGroupToSheet(WritableSheet sheet, String name, List<String> value) {
        try {
            sheet.setColumnView(0, 5);
            sheet.setColumnView(1, 40);
            sheet.setRowView(0, 15*20);
            sheet.setRowView(1, 15*20);
            sheet.setRowView(2, 40*20);
            IntStream.range(2, 1000).forEach(e -> sheet.setColumnView(e, 15));

            writeStudentsToSheet(sheet, value);
            writeDates(sheet, name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeDates(WritableSheet sheet, String name, List<String> value) throws WriteException {
        WritableCellFormat fmt = CellStyle.DATE_STYLE.format;
        writeToCell(sheet, 0, 0, CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, 0, 1, CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, 1, 0, CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, 1, 1, CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, 2, 0, "№", CellStyle.NAMES_STYLE.format);
        writeToCell(sheet, 2, 1, "ПІБ", CellStyle.NAMES_STYLE.format);

        int index = 2;
        int startLesson = startLessonNum;
        LocalDate temp = startDate.plusDays(name.contains("НД") ? 1 : 0);
        while (temp.isBefore(finalDate)) {
            writeToCell(sheet, 0, index, startLesson, fmt);
            writeToCell(sheet, 1, index, temp.format(FMT), fmt);
            writeToCell(sheet, 2, index, "", fmt);
            int finalIndex = index;
            Thread thr = new Thread(() -> {
                try {
                    for (int i = 0; i < value.size(); i++) {
                        writeToCell(sheet, i+3, finalIndex, CellStyle.BLANK_STYLE.format);
                    }
                } catch (WriteException e) {
                    throw new RuntimeException(e);
                }
            });
            thr.start();
            temp = temp.plusDays(7);
            index++;
            startLesson++;
        }
    }

    private void writeStudentsToSheet(WritableSheet sheet, List<String> value) throws WriteException {
        WritableCellFormat fmt = CellStyle.NAMES_STYLE.format;
        int index = 3;
        for (int i = 0; i < value.size(); i++, index++) {
            sheet.setRowView(index, 300);
            writeToCell(sheet, index, 0, i+1, fmt);
            writeToCell(sheet, index, 1, value.get(i), fmt);
        }
        writeToCell(sheet, index+4, 2, CellStyle.ABSENT_STYLE.format);
        writeToCell(sheet, index+4, 3, CellStyle.REWORKED_STYLE.format);
        writeToCell(sheet, index+4, 4, CellStyle.CAME_STYLE.format);
        writeToCell(sheet, index+5, 2, "не прийшов",CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, index+5, 3, "відпрацьовано", CellStyle.BLANK_STYLE.format);
        writeToCell(sheet, index+5, 4, "прийшов",CellStyle.BLANK_STYLE.format);
    }

    private void writeToCell(WritableSheet sheet, int c, int r, String text, WritableCellFormat format) throws WriteException {
        Label headerLabel = new Label(r, c, text, format);
        sheet.addCell(headerLabel);
    }

    private void writeToCell(WritableSheet sheet, int c, int r, Integer num, WritableCellFormat format) throws WriteException {
        Number headerLabel = new Number(r, c, num, format);
        sheet.addCell(headerLabel);
    }

    private void writeToCell(WritableSheet sheet, int c, int r, WritableCellFormat format) throws WriteException {
        Label headerLabel = new Label(r, c, "", format);
        sheet.addCell(headerLabel);
    }

    public void closeFile() {
        try {
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
