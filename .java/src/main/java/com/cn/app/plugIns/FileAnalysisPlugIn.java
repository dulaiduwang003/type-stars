package com.cn.app.plugIns;

import com.alibaba.fastjson.JSONObject;
import com.cn.app.enums.FileTypeEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
@SuppressWarnings("all")
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
    public JSONObject PARSE_FILE(final String dto) {

        final Vo vo = new Vo();
        final Attribute attribute = JSONObject.parseObject(dto, Attribute.class);
        final String type = attribute.getType().toUpperCase();
        try {
            InputStream inputStream = new URL(attribute.getUrl()).openStream();
            if (type.equals(FileTypeEnum.PDF.getDec())) {
                vo.setResult(readPdf(inputStream));
            } else if (type.equals(FileTypeEnum.XLSX.getDec())) {
                vo.setResult(readXlsx(inputStream));
            } else if (type.equals(FileTypeEnum.DOCX.getDec())) {
                vo.setResult(readDocx(inputStream));
            }

            final String result = vo.getResult().trim();
            if (result.length() > 500) {
                vo.setResult(result.substring(0, 600));
            }
        } catch (Exception e) {

            vo.setResult("THE DOWNLOAD LINK PROVIDED IS INVALID");
        }

        return (JSONObject) JSONObject.toJSON(vo);
    }

    private String readDocx(InputStream inputStream) {
        try {
            // 读取Docx文件内容
            XWPFDocument document = null;

            document = new XWPFDocument(inputStream);
            StringBuilder content = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                content.append(paragraph.getText()).append("\n");
            }
            document.close();
            return content.toString();
        } catch (Exception e) {
            return "THE DOCX IS NOT RECOGNIZED";
        }
    }

    private String readPdf(InputStream inputStream) {
        try {
            PDDocument document;
            document = PDDocument.load(inputStream);

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            document.close();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "THE PDF IS NOT RECOGNIZED";
        }
    }

    private String readXlsx(InputStream inputStream) {
        try {
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
        } catch (Exception e) {
            return "THE XLSX IS NOT RECOGNIZED";
        }
    }
}
