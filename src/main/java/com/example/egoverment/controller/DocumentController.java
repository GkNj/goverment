package com.example.egoverment.controller;


import com.example.egoverment.entity.Document;
import com.example.egoverment.entity.User;
import com.example.egoverment.util.readFileUtil;
import com.example.egoverment.service.serviceImpl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
public class DocumentController {

    @Autowired
    private DocumentServiceImpl documentService;

    /**
     * 处理传过来的头部文件和尾部文件
     *
     * @return
     */
    @RequestMapping("/uploadDesignDocument")
    public ModelAndView uploadDesignDocument(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,ModelAndView mv,HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id=user.getId();
        String documentName = request.getParameter("documentName");
        documentName=documentName+".doc";
        Document document=new Document();
        String originalFilename1 = file1.getOriginalFilename();
        String originalFilename2 = file2.getOriginalFilename();
        String filePath1 = "E:\\uploadFile\\document";
        File dest1 = new File(filePath1 + "/" + originalFilename1);
        File dest2 = new File(filePath1 + "/" + originalFilename2);
        if (!dest1.getParentFile().exists()&&!dest2.getParentFile().exists()) {
            dest1.getParentFile().mkdirs();
            dest2.getParentFile().mkdirs();
        }
        try {
            file1.transferTo(dest1);
            file2.transferTo(dest2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] srcDocxs={String.valueOf(dest1), String.valueOf(dest2)};
        UUID uuid = UUID.randomUUID();
        String filePath = "E:\\merge"+"\\"+uuid+".doc";
        readFileUtil readFileUtil=new readFileUtil();
        readFileUtil.mergeDoc(srcDocxs,filePath);
        document.setUserId(id);
        document.setDocumentName(documentName);
        document.setPath(filePath);
        document.setExtension("doc");
        document.setType("template");
        documentService.saveDocument(document);
        mv.setViewName("redirect:/findDocumentTemplate");
        return mv;
    }

    /**
     * 上传文件
     *
     * @return
     */
    @RequestMapping("/uploadDocument")
    public ModelAndView uploadDocument(@RequestParam("file") MultipartFile file, ModelAndView mv) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = user.getId();
        Document document = new Document();
        //得到公文名
        String documentName = file.getOriginalFilename();
        //获取公文后缀名
        String extension = documentName.substring(documentName.lastIndexOf(".") + 1);
        String filePath = "E:\\uploadFile\\document";
        File dest = new File(filePath + "/" + documentName);
        document.setUserId(id);
        document.setDocumentName(documentName);
        document.setExtension(extension);
        document.setPath(String.valueOf(dest));
        document.setType("document");
        documentService.saveDocument(document);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mv.setViewName("redirect:/findDocument");
        return mv;
    }

    /**
     * 公文下载
     *
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public String download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String documentName = request.getParameter("documentName");
        String path = request.getParameter("path");
        if (documentName != null) {
            File file = new File(path);
            if (file.exists()) {
                response.setContentType("application/force-download");//设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(documentName, "utf-8"));//设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

    @RequestMapping("/findDocument")
    public String findDocument(ModelMap map) {
        List<Document> list = documentService.findAllDocumentByType("document");
        map.addAttribute("list", list);
        return "document/official_document_upload";
    }

    @RequestMapping("/findDocumentTemplate")
    public String findDocumentTemplate(ModelMap map){
        List<Document> list = documentService.findAllDocumentByType("template");
        map.addAttribute("list", list);
        return "document/official_document_list";
    }
}
