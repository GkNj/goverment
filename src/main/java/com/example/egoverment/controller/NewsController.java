package com.example.egoverment.controller;

import com.example.egoverment.entity.News;
import com.example.egoverment.entity.User;
import com.example.egoverment.service.serviceImpl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping()
public class NewsController {

    @Autowired
    private NewsServiceImpl newsService;

    /**
     * 发布新闻
     *
     * @param news
     * @return
     */
    @RequestMapping("/pubNews")
    public String pubNews(News news, @RequestParam("file") MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        news.setAddUser(user.getId());
        //得到新闻图片名字
        String picturePath = file.getOriginalFilename();
        String filePath = "E:\\e-goverment\\src\\main\\resources\\static\\login\\images";
        File dest = new File(filePath + "/" + picturePath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(date);
        news.setPubDate(format);
        String path = String.valueOf(dest).substring(41);
        news.setPath(path);
        String substring = news.getContent().substring(3, news.getContent().length() - 4);
        news.setContent(substring);
        newsService.save(news);
        return "redirect:/findAllNews";
    }

    /**
     * 查找所有新闻
     *
     * @param map
     * @return
     */
    @RequestMapping("/findAllNews")
    public String findAllNews(ModelMap map) {
        List<News> list = newsService.findAllNews();
        map.addAttribute("list", list);
        System.out.println(list.toString());
        return "news/news_list";
    }

    /**
     * 通过ID查找新闻
     *
     * @param request
     * @return
     */
    @RequestMapping("/findNewsById")
    @ResponseBody
    public News findNewsById(HttpServletRequest request) {
        String id = request.getParameter("id");
        News news = newsService.findNewsById(Integer.parseInt(id));
        return news;
    }

    @RequestMapping("/findNewsByid")
    public String findNewsByid(HttpServletRequest request,ModelMap map) {
        String id = request.getParameter("id");
        News news = newsService.findNewsById(Integer.parseInt(id));
        map.addAttribute("news",news);
        return "news_detail";
    }
    /**
     * 更新新闻
     *
     * @param news
     * @return
     */
    @RequestMapping("/updateNews")
    public String updateNews(News news) {
        int id = news.getId();
        News news1 = newsService.findNewsById(id);
        news1.setTitle(news.getTitle());
        news1.setContent(news.getContent());
        newsService.save(news1);
        return "redirect:/findAllNews";
    }

    /**
     * 删除新闻
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteNewsById")
    public String deleteNewsById(HttpServletRequest request) {
        String id = request.getParameter("id");
        newsService.deleteNewsById(Integer.parseInt(id));
        return "redirect:/findAllNews";
    }
}
