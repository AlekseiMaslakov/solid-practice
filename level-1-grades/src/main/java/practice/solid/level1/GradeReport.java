package practice.solid.level1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Формирует ведомость успеваемости группы по CSV-файлу с оценками.
 * <p>
 * Формат входного файла: первая строка — заголовок, далее строки вида
 * {@code Имя;Предмет;Балл}, где балл от 0 до 100.
 */
public class GradeReport {

    public void generate(Path input, Path output, String format) throws IOException {
        // читаем файл
        List<String> lines = Files.readAllLines(input, StandardCharsets.UTF_8);
        Map<String, List<Integer>> scores = new TreeMap<>();
        Map<String, Set<String>> subjects = new TreeMap<>();
        int lineNo = 0;
        for (String line : lines) {
            lineNo++;
            if (lineNo == 1) {
                continue; // заголовок
            }
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(";");
            if (parts.length != 3) {
                System.out.println("WARN: строка " + lineNo + " пропущена: неверное число полей");
                continue;
            }
            String name = parts[0].trim();
            String subject = parts[1].trim();
            int score;
            try {
                score = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("WARN: строка " + lineNo + " пропущена: балл не является числом");
                continue;
            }
            if (score < 0 || score > 100) {
                System.out.println("WARN: строка " + lineNo + " пропущена: балл вне диапазона 0..100");
                continue;
            }
            scores.computeIfAbsent(name, k -> new ArrayList<>()).add(score);
            subjects.computeIfAbsent(name, k -> new TreeSet<>()).add(subject);
        }

        // считаем и форматируем
        StringBuilder sb = new StringBuilder();
        if (format.equals("txt")) {
            sb.append("ВЕДОМОСТЬ УСПЕВАЕМОСТИ\n");
            sb.append("======================\n");
        } else if (format.equals("html")) {
            sb.append("<html><body>\n");
            sb.append("<h1>Ведомость успеваемости</h1>\n");
            sb.append("<table>\n");
            sb.append("<tr><th>Студент</th><th>Предметов</th><th>Средний балл</th><th>Оценка</th></tr>\n");
        } else {
            throw new IllegalArgumentException("Неизвестный формат: " + format);
        }

        double total = 0;
        int count = 0;
        for (String name : scores.keySet()) {
            List<Integer> s = scores.get(name);
            double sum = 0;
            for (int x : s) {
                sum += x;
            }
            double avg = sum / s.size();
            total += avg;
            count++;

            String grade;
            if (avg >= 90) {
                grade = "отлично";
            } else if (avg >= 75) {
                grade = "хорошо";
            } else if (avg >= 60) {
                grade = "удовлетворительно";
            } else {
                grade = "неудовлетворительно";
            }

            String avgStr = String.format(Locale.US, "%.1f", avg);
            if (format.equals("txt")) {
                sb.append(name)
                        .append(": предметов ").append(subjects.get(name).size())
                        .append(", средний балл ").append(avgStr)
                        .append(" (").append(grade).append(")\n");
            } else {
                sb.append("<tr><td>").append(name)
                        .append("</td><td>").append(subjects.get(name).size())
                        .append("</td><td>").append(avgStr)
                        .append("</td><td>").append(grade)
                        .append("</td></tr>\n");
            }
        }

        String groupAvg = count == 0 ? "-" : String.format(Locale.US, "%.1f", total / count);
        if (format.equals("txt")) {
            sb.append("----------------------\n");
            sb.append("Средний балл группы: ").append(groupAvg).append("\n");
        } else {
            sb.append("</table>\n");
            sb.append("<p>Средний балл группы: ").append(groupAvg).append("</p>\n");
            sb.append("</body></html>\n");
        }

        // записываем результат
        Files.writeString(output, sb.toString(), StandardCharsets.UTF_8);
        System.out.println("Отчёт сохранён: " + output);
    }
}
