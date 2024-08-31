package com.lth.bookingcare.service;

import com.lth.bookingcare.dto.ArticleDTO;
import com.lth.bookingcare.dto.MajorDTO;
import com.lth.bookingcare.entity.Article;

import java.util.List;

public interface IArticleService {
    List<Article> getAll();
    boolean saveArticle(ArticleDTO articleDTO);
    Article findById(Long id);
    void deleteArticle(Long id);
    void convertDTOToEntity(ArticleDTO articleDTO, Article article);
}
