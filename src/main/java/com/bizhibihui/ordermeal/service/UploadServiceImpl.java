package com.bizhibihui.ordermeal.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bizhibihui.ordermeal.config.properties.FileUploadProperties;
import com.bizhibihui.ordermeal.exception.UploadFileNotFoundException;
import com.bizhibihui.ordermeal.util.CoreConst;
import com.bizhibihui.ordermeal.util.FileUploadUtil;
import com.bizhibihui.ordermeal.util.MD5;
import com.bizhibihui.ordermeal.util.ResultUtil;
import com.bizhibihui.ordermeal.vo.UploadResponse;
import com.bizhibihui.ordermeal.vo.base.ResponseVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author: andy
 * Date: 2020-08-10
 * Time: 14:46
 */
public class UploadServiceImpl implements UploadService{

    @Autowired
    private FileUploadProperties fileUploadProperties;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @Override
    @SneakyThrows
    public UploadResponse upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new UploadFileNotFoundException(UploadResponse.ErrorEnum.FILE_NOT_FOUND.msg);
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();
        String relativePath = FileUploadUtil.uploadLocal(file, fileUploadProperties.getUploadFolder());
        String accessPrefixUrl = fileUploadProperties.getAccessPrefixUrl();
        if (!StringUtils.endsWith(accessPrefixUrl, "/")) {
            accessPrefixUrl += '/';
        }
        String url = accessPrefixUrl + relativePath;
        ResponseVo<?> responseVo = ResultUtil.success();
        if (responseVo.getStatus().equals(CoreConst.SUCCESS_CODE)) {
            return UploadResponse.success(url, originalFilename, suffix, url, CoreConst.SUCCESS_CODE);
        } else {
            return UploadResponse.failed(originalFilename, CoreConst.FAIL_CODE, responseVo.getMsg());
        }
    }
}
