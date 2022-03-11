package com.examination.service;

import com.examination.entity.PaperManage;

import java.util.List;

public interface PaperService {

    public int add(PaperManage paperManage);

    public List<PaperManage> findAll();

    public List<PaperManage> findById(Integer paperId);
}
