package com.alibaba.content.mapper;

import com.alibaba.content.model.Share;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ShareMapper extends BaseMapper<Share> {

    /**
     * 查询全部分享
     *
     * @return
     */
    List<Share> findAllShare();
}