package com.derrick.big_event.mapper;

import com.derrick.big_event.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("INSERT INTO category(category_name, category_alias, create_user, create_time, update_time) " +
            " VALUES (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void saveNewCategory(Category category);

    @Select("SELECT * FROM category WHERE create_user = #{userId}")
    List<Category> getCategoriesByUser(Integer userId);

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getCategoryById(Integer id);

    @Update("UPDATE category SET category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = #{updateTime} WHERE id = #{id}")
    void updateCategory(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void deleteCategoryById(Integer id);
}
