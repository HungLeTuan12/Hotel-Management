package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.dto.ArticleDTO;
import com.lth.bookingcare.dto.MajorDTO;
import com.lth.bookingcare.entity.Article;
import com.lth.bookingcare.entity.Major;
import com.lth.bookingcare.exception.ResourceAlreadyExist;
import com.lth.bookingcare.repository.ArticleRepository;
import com.lth.bookingcare.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public boolean saveArticle(ArticleDTO articleDTO) {
        Article article = new Article();
        convertDTOToEntity(articleDTO, article);
        return articleRepository.save(article) != null;
    }
    public void updateArticle(ArticleDTO articleDTO, Long id) {
        Article article = articleRepository.findById(id).get();
        if(article.getTitle().equals(articleDTO.getTitle()) ) {
            throw new ResourceAlreadyExist("Title is already exist !");
        }
        else {
            convertDTOToEntity(articleDTO, article);
            articleRepository.save(article);
        }
    }
    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void convertDTOToEntity(ArticleDTO articleDTO, Article article) {
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setImages(articleDTO.getImages());
    }
}
