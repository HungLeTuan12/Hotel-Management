package com.lth.bookingcare.controller;

import com.lth.bookingcare.dto.ArticleDTO;
import com.lth.bookingcare.entity.Article;
import com.lth.bookingcare.service.impl.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ErrorResponse;
import response.SuccessResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("/create-article")
    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO articleDTO) {
        try {
            articleService.saveArticle(articleDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Create article successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @PutMapping("/article/{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleDTO articleDTO,
                                           @PathVariable("id") Long id
                                           ){
        try {
            Article article = articleService.findById(id);
            if(article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Article not found ! "));
            }
            articleService.updateArticle(articleDTO, id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Update article successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticle() {
        try {
            List<Article> articles = articleService.getAll();
            return ResponseEntity.ok().body(new SuccessResponse<>("Articles", articles));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
    @DeleteMapping("/delete-article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) {
        try {
            Article article = articleService.findById(id);
            if(article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse<>("Article not found ! "));
            }
            articleService.deleteArticle(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SuccessResponse<>("Delete article successfully ! "));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse<>("Server error !"));
        }
    }
}
