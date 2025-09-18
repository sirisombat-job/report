package com.example.demo.service;

import com.example.demo.model.Book;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.RenderOption;

public interface BookService {
    List<Book> findAllBooks();
    Book findBookById(int id);
    void deleteAllBooks();    
    byte[] generateReport(String reportName, Map<String, Object> parameters, RenderOption options, HttpServletResponse response) throws Exception;
}