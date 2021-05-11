package com.alibaba.content.service;

import com.alibaba.content.dto.ShareDTO;
import com.alibaba.content.model.Share;

import java.util.List;

public interface ShareService {

    public ShareDTO findById(Integer id);

    List<Share> findAllShare();
}
