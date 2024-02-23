package com.cn.app.plugIns;

import com.alibaba.fastjson.JSONObject;
import com.cn.app.enums.FileTypeEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

/**
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class FileAnalysisPlugIn {

    @Data
    @Accessors(chain = true)
    public static class Attribute implements Serializable {

        private String type;

        private String url;

    }

    @Data
    @Accessors(chain = true)
    public static class Vo implements Serializable {

        private String result;

    }

    /**
     * Parse file json object.
     *
     * @param dto the dto
     * @return the json object
     */
    @SuppressWarnings("all")
    public JSONObject PARSE_FILE(final String dto) {

        final Vo vo = new Vo();
        final Attribute attribute = JSONObject.parseObject(dto, Attribute.class);

        try {
            InputStream inputStream = new URL(attribute.getUrl()).openStream();
            if (attribute.getType().equals(FileTypeEnum.PDF.getDec())) {
                vo.setResult(readPdf(inputStream));
            } else if (attribute.getType().equals(FileTypeEnum.XLSX.getDec())) {
                vo.setResult(readXlsx(inputStream));
            } else if (attribute.getType().equals(FileTypeEnum.DOCX.getDec())) {
                vo.setResult(readDocx(inputStream));
            }
        } catch (Exception e) {
            return (JSONObject) JSONObject.toJSON(vo.setResult(e.getMessage()));
        }
        return (JSONObject) JSONObject.toJSON(vo);
    }

    private String readDocx(InputStream inputStream) {
        // 读取Docx文件内容
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
            StringBuilder content = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                content.append(paragraph.getText()).append("\n");
            }
            document.close();
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EmptyFileException e) {
            return "";
        }
    }

    private String readPdf(InputStream inputStream) {
        PDDocument document;
        try {
            document = PDDocument.load(inputStream);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EmptyFileException e) {
            return "";
        }
    }

    private String readXlsx(InputStream inputStream) {
        StringBuilder str = new StringBuilder();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = workbook.getSheetAt(0);
        for (Row currentRow : sheet) {
            for (Cell currentCell : currentRow) {
                if (currentCell.getCellType() == CellType.STRING) {
                    str.append(currentCell.getStringCellValue()).append("\t");
                } else if (currentCell.getCellType() == CellType.NUMERIC) {
                    str.append(currentCell.getNumericCellValue()).append("\t");
                }
            }
            str.append("\n");
        }
        return str.toString().trim();
    }
}
