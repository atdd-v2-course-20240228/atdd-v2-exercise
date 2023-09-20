package com.odde.atddv2.entity.relations;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne
    private School school;

    @ManyToOne
    private Teacher teacher;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<Student> students = new ArrayList<>();

    public void toSheet(XSSFWorkbook workbook) {
        Sheet sheet = workbook.createSheet(getName());
        int r, c = 0;
        Row headerRow = sheet.createRow(r = 0);
        for (String text : Arrays.asList("姓名", "语文", "英语", "数学", "加分", "总分"))
            headerRow.createCell(c++).setCellValue(text);
        for (Student student : getStudents()) {
            Row row = sheet.createRow(++r);
            row.createCell(c = 0).setCellValue(student.getName());
            row.createCell(++c).setCellValue(student.getChinese());
            row.createCell(++c).setCellValue(student.getEnglish());
            row.createCell(++c).setCellValue(student.getMath());
            row.createCell(++c).setCellValue(student.getBonus());
            row.createCell(++c).setCellFormula(String.format("B%d+C%d+D%d+E%d", r + 1, r + 1, r + 1, r + 1));
        }
    }

    public double getChinese() {
        return students.stream().mapToDouble(Student::getChinese).average().orElse(0);
    }

    public double getEnglish() {
        return students.stream().mapToDouble(Student::getEnglish).average().orElse(0);
    }

    public double getMath() {
        return students.stream().mapToDouble(Student::getMath).average().orElse(0);
    }
}
