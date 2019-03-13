package com.example.egoverment.service;

import com.example.egoverment.entity.News;

import java.util.List;

public interface NewsService {
    /**
     * 发布新闻
     *
     * @param news
     * @return
     */
    News save(News news);

    /**
     * 查找所有新闻
     *
     * @return
     */
    List<News> findAllNews();

    /**
     * 通过ID查询新闻
     *
     * @param id
     * @return
     */
    News findNewsById(int id);

    /**
     * 通过ID删除新闻
     *
     * @param id
     * @return
     */
    int deleteNewsById(int id);
}
