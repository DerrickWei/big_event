package com.derrick.big_event.mapper;

import com.derrick.big_event.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("INSERT INTO article (title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            " VALUES (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void addNewArticle(Article article);


    List<Article> getArticles(Integer categoryId, String state, Integer userId);

    @Select("SELECT * FROM article WHERE id = #{id}")
    Article getArticleById(Integer id);

    @Update("UPDATE article SET title = #{title}, content = #{content}, cover_img = #{coverImg}, state = #{state}, category_id = #{categoryId}, create_user = #{createUser} " +
            " WHERE id = #{id}")
    void updateArticle(Article article);

    @Delete("DELETE FROM article WHERE id = #{id}")
    void deleteArticleById(Integer id);
}
