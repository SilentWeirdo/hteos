package com.hteos.biz.app.service;

import com.hteos.biz.app.entity.App;
import com.hteos.biz.installation.dto.AppDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface AppService {



    @Transactional(readOnly = true)
    Page<AppDto> search(String condition, int size, int page);

    @Transactional(readOnly = true)
    App get(String id);

    @Transactional
    String save(AppDto appDto);

    @Transactional
    void delete(String ids);

    AppDto getDto(String id);

    @Transactional
    void doUpload(String id, MultipartFile icon, MultipartFile image);
}
