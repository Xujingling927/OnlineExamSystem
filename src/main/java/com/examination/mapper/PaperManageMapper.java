package com.examination.mapper;

import com.examination.entity.PaperManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperManageMapper {
    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    int add(PaperManage paperManage);
}
