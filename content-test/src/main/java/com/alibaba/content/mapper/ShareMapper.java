package com.alibaba.content.mapper;

import com.alibaba.content.model.Share;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
public interface ShareMapper extends BaseMapper<Share> {

    /**
     * 查询全部分享
     *
     * @return
     */
    List<Share> findAllShare();
}