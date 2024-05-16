package com.derrick.big_event.service;

import com.derrick.big_event.pojo.Article;
import com.derrick.big_event.pojo.PageBean;

public interface ArticleService {
    void addNewArticle(Article article);

    PageBean<Article> getArticles(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article getArticleById(Integer id);

    void updateArticle(Article article);

    void deleteArticleById(Integer id);
}
