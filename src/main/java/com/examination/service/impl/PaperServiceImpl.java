package com.examination.service.impl;

import com.examination.entity.PaperManage;
import com.examination.mapper.PaperManageMapper;
import com.examination.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperManageMapper paperManageMapper;
    @Autowired
    public PaperServiceImpl(PaperManageMapper paperManageMapper) {
        this.paperManageMapper = paperManageMapper;
    }

    @Override
    public int add(PaperManage paperManage) {
        return paperManageMapper.add(paperManage);
    }

    @Override
    public List<PaperManage> findAll() {
        return paperManageMapper.findAll();
    }

    @Override
    public List<PaperManage> findById(Integer paperId) {
        return paperManageMapper.findById(paperId);
    }
}
