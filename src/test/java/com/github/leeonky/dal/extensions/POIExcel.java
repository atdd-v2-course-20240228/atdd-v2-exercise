package com.github.leeonky.dal.extensions;

import com.github.leeonky.cucumber.restful.RestfulStep;
import com.github.leeonky.dal.DAL;
import com.github.leeonky.dal.runtime.Extension;
import com.github.leeonky.dal.runtime.JavaClassPropertyAccessor;
import com.github.leeonky.dal.runtime.ListAccessor;
import com.github.leeonky.dal.runtime.RuntimeContextBuilder;
import com.github.leeonky.util.BeanClass;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.leeonky.dal.Assertions.expect;
import static com.github.leeonky.dal.extensions.BinaryExtension.readAll;
import static com.github.leeonky.dal.extensions.POIExcel.StaticMethods.excel;
import static java.util.Optional.ofNullable;

public class POIExcel implements Extension {

    @Override
    public void extend(DAL dal) {
        FileGroup.register("xlsx", inputStream -> excel(readAll(inputStream)));
        RuntimeContextBuilder runtimeContextBuilder = dal.getRuntimeContextBuilder();
        runtimeContextBuilder
                .registerStaticMethodExtension(StaticMethods.class)
                .registerPropertyAccessor(Workbook.class, new JavaClassPropertyAccessor<>(runtimeContextBuilder, BeanClass.create(Workbook.class)) {
                    @Override
                    public Object getValue(Workbook workbook, Object name) {
                        try {
                            return super.getValue(workbook, name);
                        } catch (Exception ignore) {
                            return ofNullable(workbook.getSheet((String) name)).orElseThrow(() ->
                                    new IllegalArgumentException("sheet or property '" + name + "' not exist"));
                        }
                    }

                    @Override
                    public Set<Object> getPropertyNames(Workbook workbook) {
                        return IntStream.range(0, workbook.getNumberOfSheets()).mapToObj(workbook::getSheetAt).map(Sheet::getSheetName)
                                .collect(Collectors.toSet());
                    }
                })
                .registerListAccessor(XSSFSheet.class, new ListAccessor<>() {
                    @Override
                    public Iterable<?> toIterable(XSSFSheet sheet) {
                        return IntStream.range(0, sheet.getLastRowNum() + 1).mapToObj(sheet::getRow).collect(Collectors.toList());
                    }

                    @Override
                    public int firstIndex(XSSFSheet instance) {
                        return 1;
                    }
                })
                .registerPropertyAccessor(Row.class, new JavaClassPropertyAccessor<>(runtimeContextBuilder, BeanClass.create(Row.class)) {

                    @Override
                    public Object getValue(Row row, Object name) {
                        try {
                            return super.getValue(row, name);
                        } catch (Exception ignore) {
                            CellReference cellReference = new CellReference((String) name);
                            return ofNullable(row.getCell(cellReference.getCol())).orElseThrow(() ->
                                    new IllegalArgumentException("cell or property '" + name + "' not exist"));
                        }
                    }

                    @Override
                    public Set<Object> getPropertyNames(Row instance) {
                        return IntStream.range(0, instance.getLastCellNum()).mapToObj(i -> String.valueOf((char) ('A' + i))).collect(Collectors.toSet());
                    }
                });
        runtimeContextBuilder.getConverter()
                .addTypeConverter(Cell.class, Double.class, Cell::getNumericCellValue)
                .addTypeConverter(Cell.class, Integer.class, this::toInt)
                .addTypeConverter(Cell.class, Long.class, this::toLong)
                .addTypeConverter(Cell.class, String.class, cell -> cell.getStringCellValue());
    }

    private int toInt(Cell cell) {
        double doubleValue = cell.getNumericCellValue();
        int intValue = (int) doubleValue;
        if (Math.abs(doubleValue - intValue) > 0.00000001d)
            throw new IllegalArgumentException("Cannot convert " + doubleValue + " to int");
        return intValue;
    }

    private long toLong(Cell cell) {
        double doubleValue = cell.getNumericCellValue();
        long longValue = (long) doubleValue;
        if (Math.abs(doubleValue - longValue) > 0.00000001d)
            throw new IllegalArgumentException("Cannot convert " + doubleValue + " to long");
        return longValue;
    }

    public static class StaticMethods {
        @SneakyThrows
        public static XSSFWorkbook excel(byte[] data) {
            return new XSSFWorkbook(new ByteArrayInputStream(data));
        }

        public static Object 成绩单(RestfulStep.Response response) {
            return expect(response).get("body.unzip[0].excel[1]");
        }
    }
}
