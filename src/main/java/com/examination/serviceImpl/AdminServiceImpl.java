package com.examination.serviceImpl;

import com.examination.entity.Admin;
import com.examination.mapper.AdminMapper;
import com.examination.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public List<Admin> findAll() {
        return adminMapper.findAll();
    }

    @Override
    public Admin findById(Integer adminId) {
        return adminMapper.findById(adminId);
    }

    @Override
    public int deleteById(Integer adminId) {
        return adminMapper.deleteById(adminId);
    }

    @Override
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public int updatePwd(Integer adminId, String pwd) {
        return adminMapper.updatePwd(adminId,pwd);
    }


    @Override
    public int add(Admin admin) {
        return adminMapper.add(admin);
    }
}
