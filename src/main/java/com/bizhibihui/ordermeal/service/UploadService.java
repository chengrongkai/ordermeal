package com.bizhibihui.ordermeal.service;

import com.bizhibihui.ordermeal.vo.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author: andy
 * Date: 2020-08-10
 * Time: 14:44
 */
public interface UploadService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    UploadResponse upload(MultipartFile file);
}
