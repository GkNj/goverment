package com.example.egoverment.controller;

import com.example.egoverment.entity.UploadFile;
import com.example.egoverment.entity.User;
import com.example.egoverment.service.serviceImpl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @RequestMapping("/findFile")
    @ResponseBody
    public ModelAndView findFile(ModelAndView mv, HttpServletRequest request) {
        String fileType = request.getParameter("fileType");
        List<UploadFile> files = uploadFileService.findFileByFileType(fileType);
        mv.addObject("list", files);
        System.out.println(files.toString());
        if (fileType.equals("财政部")) {
            mv.setViewName("file/exchequer_file_list");
        }
        if (fileType.equals("城建部")) {
            mv.setViewName("file/city_file_list");
        }
        if (fileType.equals("交通部")) {
            mv.setViewName("file/traffic_file_list");
        }
        if (fileType.equals("新闻部")) {
            mv.setViewName("file/news_file_list");
        }
        if (fileType.equals("后勤部")) {
            mv.setViewName("file/logistics_file_list");
        }
        return mv;
    }

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping("/uploadFile")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "fileType") String fileType, ModelAndView mv) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = user.getId();
        //得到文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(name);
        uploadFile.setExtension(extension);
        uploadFile.setFileType(fileType);
        uploadFile.setUserId(id);
        String filePath = "E:\\uploadFile\\temp";
        uploadFile.setPath(filePath);
        System.out.println(uploadFile.toString());
        uploadFileService.saveFile(uploadFile);
        File dest = new File(filePath + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mv.addObject("fileType", fileType);
        mv.setViewName("redirect:/file/findFile");
        return mv;
    }

    @RequestMapping("/deleteFileById")
    public ModelAndView deleteFileById(HttpServletRequest request, ModelAndView mv){
        String id = request.getParameter("id");
        String fileType = request.getParameter("fileType");
        System.out.println(fileType);
        uploadFileService.deleteFile(Integer.parseInt(id));
        mv.addObject("fileType", fileType);
        mv.setViewName("redirect:/file/findFile");
        return mv;
    }
}
