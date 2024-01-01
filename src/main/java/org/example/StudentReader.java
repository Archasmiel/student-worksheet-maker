package org.example;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StudentReader {

    public static final int NEWLINE_CODE = 10;
    public static final int WAVE_CODE = 126;

    private List<FileMaker.Pair<String, List<String>>> classes;

    public StudentReader(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("shit");
            return;
        }

        boolean wasWaveCode = false;
        boolean redClassName = false;
        classes = new ArrayList<>();
        StringBuilder className = new StringBuilder();

        try (FileReader reader = new FileReader(file)) {
            List<String> pibs = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int ch;

            while ((ch = reader.read()) != -1) {
                switch (ch) {
                    case WAVE_CODE -> {
                        wasWaveCode = true;
                        if (!pibs.isEmpty() && !className.isEmpty()) {
                            classes.add(new FileMaker.Pair<>(className.toString().strip(), pibs));
                        }
                        redClassName = false;
                        pibs = new ArrayList<>();
                        className = new StringBuilder();
                    }
                    case NEWLINE_CODE -> {
                        if (wasWaveCode) {
                            wasWaveCode = false;
                            continue;
                        }
                        if (!redClassName) {
                            redClassName = true;
                            continue;
                        }
                        if (!sb.isEmpty()) {
                            pibs.add(sb.toString().strip());
                        }
                        sb = new StringBuilder();
                    }
                    default -> {
                        if (!redClassName) {
                            className.append((char) ch);
                        } else {
                            sb.append((char) ch);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FileMaker.Pair<String, List<String>>> getClasses() {
        return classes;
    }
}
