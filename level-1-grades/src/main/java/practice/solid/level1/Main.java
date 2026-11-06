package practice.solid.level1;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {
        Path input = Path.of(args.length > 0 ? args[0] : "level-1-grades/data/grades.csv");
        String format = args.length > 1 ? args[1] : "txt";
        Path output = Path.of("report." + format);
        new GradeReport().generate(input, output, format);
    }
}
