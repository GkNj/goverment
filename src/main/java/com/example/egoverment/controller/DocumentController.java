package com.example.egoverment.controller;

import com.example.egoverment.entity.Document;
import com.example.egoverment.entity.User;
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

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentServiceImpl documentService;

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
        documentService.saveDocument(document);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mv.setViewName("redirect:/document/findDocument");
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
        List<Document> list = documentService.findAllDocument();
        map.addAttribute("list", list);
        return "document/official_document_upload";
    }
}
