package com.cn.app.service;

import com.cn.app.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bdth github@dulaiduwang003
 * @version 1.0
 */
public interface OssService {


//    /**
//     * Upload file file vo.
//     *
//     * @param file the file
//     * @return the file vo
//     */
//    FileVo uploadFile(MultipartFile file);


    FileVo uploadFile(MultipartFile file);

}
