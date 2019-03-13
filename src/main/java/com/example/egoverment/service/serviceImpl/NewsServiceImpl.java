package com.example.egoverment.service.serviceImpl;

import com.example.egoverment.entity.News;
import com.example.egoverment.repository.NewsRepository;
import com.example.egoverment.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Override
    public News save(News news) {
        News news1 = newsRepository.save(news);
        return news1;
    }

    @Override
    public List<News> findAllNews() {
        List<News> list = newsRepository.findAll();
        return list;
    }

    @Override
    public News findNewsById(int id) {
        return newsRepository.findNewsById(id);
    }

    @Transactional
    @Override
    public int deleteNewsById(int id) {
        return newsRepository.deleteNewsById(id);
    }
}
