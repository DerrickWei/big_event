package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Article;
import com.derrick.big_event.pojo.PageBean;
import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addNewArticle(@RequestBody @Validated Article article) {
        articleService.addNewArticle(article);

        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> getArticles(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> pbArticles = articleService.getArticles(pageNum, pageSize, categoryId, state);

        return Result.success(pbArticles);
    }

    @GetMapping("/detail")
    public Result<Article> getArticleById(@RequestParam("id") Integer id) {
        Article article = articleService.getArticleById(id);

        return Result.success(article);
    }

    @PutMapping
    public Result updateArticle(@RequestBody @Validated Article article) {
        articleService.updateArticle(article);

        return Result.success();
    }

    @DeleteMapping
    public Result deleteArticleById(@RequestParam("id") Integer id) {
        articleService.deleteArticleById(id);

        return Result.success();
    }
}
