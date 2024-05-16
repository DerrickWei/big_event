package com.derrick.big_event.service.impl;

import com.derrick.big_event.mapper.ArticleMapper;
import com.derrick.big_event.pojo.Article;
import com.derrick.big_event.pojo.PageBean;
import com.derrick.big_event.service.ArticleService;
import com.derrick.big_event.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addNewArticle(Article article) {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // Set create_user, create_time and update_time
        article.setCreateUser(userId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.addNewArticle(article);
    }

    @Override
    public PageBean<Article> getArticles(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // Create PageBean object
        PageBean<Article> pageBean = new PageBean<>();

        // Start page helper to set up pagination
        PageHelper.startPage(pageNum, pageSize);

        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        // Get articles from articleMapper function
        List<Article> articles = articleMapper.getArticles(categoryId, state, userId);

        // Page can get total items number and articles from current page
        Page<Article> page = (Page<Article>) articles;

        // Put to PageBean
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public void updateArticle(Article article) {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        article.setCreateUser(userId);
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticleById(Integer id) {
        articleMapper.deleteArticleById(id);
    }
}
