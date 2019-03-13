package com.example.egoverment.repository;

import com.example.egoverment.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    /**
     * 通过ID查找新闻
     *
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
