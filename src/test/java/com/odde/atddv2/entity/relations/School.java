package com.odde.atddv2.entity.relations;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Getter
@Setter
@Entity
@Accessors(chain = true)
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name, address;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<Clazz> clazzes = new ArrayList<>();

    @SneakyThrows
    public void archive(ZipOutputStream zipOutputStream) {
        zipOutputStream.putNextEntry(new ZipEntry(name + ".xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook();
        createCover(workbook);
        clazzes.forEach(clazz -> clazz.toSheet(workbook));
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
        workbook.write(zipOutputStream);
        workbook.close();
    }

    private void createCover(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("班级");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("学校:");
        row.createCell(1).setCellValue(name);
        row = sheet.createRow(1);
        row.createCell(0).setCellValue("班级总数:");
        row.createCell(1).setCellValue(clazzes.size());
        row = sheet.createRow(2);
        row.createCell(0).setCellValue("学生总数:");
        row.createCell(1).setCellValue(clazzes.stream().mapToInt(clazz -> clazz.getStudents().size()).sum());

        int r, c = 0;
        Row headerRow = sheet.createRow(r = 4);
        for (String text : Arrays.asList("班级", "班主任", "语文", "英语", "数学"))
            headerRow.createCell(c++).setCellValue(text);
        for (Clazz clazz : clazzes) {
            row = sheet.createRow(++r);
            row.createCell(c = 0).setCellValue(clazz.getName());
            row.createCell(++c).setCellValue(clazz.getTeacher().getName());
            row.createCell(++c).setCellValue(clazz.getChinese());
            row.createCell(++c).setCellValue(clazz.getEnglish());
            row.createCell(++c).setCellValue(clazz.getMath());
        }
    }
}
