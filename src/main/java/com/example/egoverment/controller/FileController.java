package com.example.egoverment.controller;

import com.example.egoverment.entity.UploadFile;
import com.example.egoverment.entity.User;
import com.example.egoverment.service.serviceImpl.UploadFileServiceImpl;
import com.example.egoverment.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping()
public class FileController {

    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping("/findFile")
    @ResponseBody
    public ModelAndView findFile(ModelAndView mv, HttpServletRequest request) {
        String fileType = request.getParameter("fileType");
        List<UploadFile> files = uploadFileService.findFileByFileType(fileType);
        System.out.println(files.toString());
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
        if (fileType.equals("政府")) {
            mv.setViewName("file/government_file_list");
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
        String filePath = "E:\\uploadFile\\temp\\demo";
        System.out.println(uploadFile.toString());
        File dest = new File(filePath + "/" + fileName);
        uploadFile.setPath(fileName);
        uploadFileService.saveFile(uploadFile);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mv.addObject("fileType", fileType);
        mv.setViewName("redirect:/findFile");
        return mv;
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public int uploadImage(@RequestParam("file") String file, ModelAndView mv) {
        String base64Data = file.split(",")[1];
        /**
         * 2.解码成字节数组
         */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64Data);
        /**
         * 3.字节流转文件
         */
        FileOutputStream fos = null;
        UUID uuid = UUID.randomUUID();
        String filePath = "E:\\e-goverment\\src\\main\\resources\\static\\login\\images" + uuid;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = user.getId();
        User user1 = userService.findUserById(id);
//        String filePath = "E:\\e-goverment\\src\\main\\resources\\static\\login\\images";
//        File dest = new File(filePath);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//
//            fos.transferTo(dest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String path = filePath.substring(41);
        user1.setImage(path);
        user.setImage(path);
        userService.updateUser(user1);
        mv.setViewName("redirect:/index");
        return 1;
    }


    @RequestMapping("/deleteFileById")
    public ModelAndView deleteFileById(HttpServletRequest request, ModelAndView mv) {
        String id = request.getParameter("id");
        String fileType = request.getParameter("fileType");
        System.out.println(fileType);
        uploadFileService.deleteFile(Integer.parseInt(id));
        mv.addObject("fileType", fileType);
        mv.setViewName("redirect:/findFile");
        return mv;
    }

    @RequestMapping("/findFileGroupByType")
    @ResponseBody
    public List findFileGroupByType() {
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list = uploadFileService.findFileGroupByType();

        for (int i = 0; i < list.size(); i++) {
            Object[] o = (Object[]) list.get(i);
            list1.add(o[0]);
            list2.add(o[1]);
        }
        list3.add(list1);
        list3.add(list2);
        return list3;
    }
}
