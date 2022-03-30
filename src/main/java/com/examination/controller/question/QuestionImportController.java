package com.examination.controller.question;

import com.examination.entity.Result;
import com.examination.service.question.QuestionImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@Api(tags = "题库导入")
public class QuestionImportController {

    QuestionImportService service;

    @Autowired
    public QuestionImportController(QuestionImportService service) {
        this.service = service;
    }

    @PostMapping(value="/question/upload",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传文件",consumes = "multipart/form-data",produces = "multipart/form-data")
    @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = MultipartFile.class)
    public Result upload( @ApiParam(name = "file",value = "file", required = true) MultipartFile file) {
        // 判断是否有文件
        if (file.isEmpty()) {
            return Result.fail("error",404);
        }
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        // 判断是不是可以接收的文件类型
        String avatarFileSuffix = ".csv";
        if (!avatarFileSuffix.contains(suffix)) {
            return Result.fail("error",404);
        }
        // 用uuid作为新的文件名
        String fileName = UUID.randomUUID() +"."+suffix;

        try {
            // 获取静态资源路径
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()){
                path=new File("");
            }
            // 设置存放路径
            File upload=new File(path.getAbsolutePath(),"static/upload/"+fileName);
            // 如果目录不存在就创建目录
            if(!upload.exists()){
                upload.mkdirs();
            }
            // 写入
            file.transferTo(upload);
            return Result.success(service.add("/root/target/static/upload/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("error",404);
        }

    }

    @ApiOperation("导入模版下载")
    @GetMapping("/question/download")
    public String fileDownLoad(HttpServletResponse response) {
        String path = "/root/src/main/resources/import/";
        String fileName = "questionImprt.csv";
        File file = new File(path+fileName);
        if (!file.exists()) {
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";

    }
}
