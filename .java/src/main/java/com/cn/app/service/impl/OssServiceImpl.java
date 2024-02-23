package com.cn.app.service.impl;

import com.cn.app.enums.FileContentTypeEnum;
import com.cn.app.enums.FilePathEnum;
import com.cn.app.enums.FileTypeEnum;
import com.cn.app.exceptions.OssException;
import com.cn.app.service.OssService;
import com.cn.app.utils.UploadUtil;
import com.cn.app.vo.FileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Oss service.
 *
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OssServiceImpl implements OssService {

    private final UploadUtil uploadUtil;

    @SuppressWarnings("all")
    public FileVo uploadFile(final MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            switch (contentType) {
                case "application/pdf":
                    return uploadFileForContentType(file,
                            FileContentTypeEnum.PDF.getDec(),
                            FileTypeEnum.PDF.getDec(),
                            FilePathEnum.TEMP.getDec());
                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                    return uploadFileForContentType(file,
                            FileContentTypeEnum.XLSX.getDec(),
                            FileTypeEnum.XLSX.getDec(),
                            FilePathEnum.TEMP.getDec());
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    return uploadFileForContentType(file,
                            FileContentTypeEnum.DOCX.getDec(),
                            FileTypeEnum.DOCX.getDec(),
                            FilePathEnum.TEMP.getDec());
                case "image/jpeg":
                case "image/jpg":
                case "image/png":
                    return uploadFileForContentType(file,
                            FileContentTypeEnum.IMAGE.getDec(),
                            FileTypeEnum.IMAGE.getDec(),
                            FilePathEnum.TEMP.getDec());
            }
        }

        throw new OssException("不支持上传该类型文件");
    }

    private FileVo uploadFileForContentType(MultipartFile file, String contentType, String fileType, String filePath) {
        final String url = uploadUtil.uploadFile(file, contentType, filePath);
        return new FileVo()
                .setFileUrl(url)
                .setFileType(fileType);
    }

}
