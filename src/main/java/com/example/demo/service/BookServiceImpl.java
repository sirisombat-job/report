package com.example.demo.service;

import com.example.demo.model.Book;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.*;


@Service
public class BookServiceImpl implements BookService {
    private List<Book> books = new ArrayList<>();
    private IReportEngine birtEngine;

    @Autowired
    public BookServiceImpl(IReportEngine reportEngine) {
        this.birtEngine = reportEngine;
        
		// Sample data for books
        books.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book(2, "1984", "George Orwell"));
        books.add(new Book(3, "To Kill a Mockingbird", "Harper Lee"));
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }

    @Override
    public Book findBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deleteAllBooks() {
        books.clear();
    }

    
    @Override
    public byte[] generateReport(String reportName, Map<String, Object> parameters, RenderOption options, HttpServletResponse response) throws Exception {
        try (InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName + ".rptdesign")) {
        	IReportRunnable design = birtEngine.openReportDesign(reportStream);
            IRunAndRenderTask task = birtEngine.createRunAndRenderTask(design);

            if (parameters != null) {
                task.setParameterValues(parameters);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            options.setOutputStream(outputStream);
            task.setRenderOption(options);

            task.run();
            task.close();
            //System.out.println("Report generated successfully!");
            return outputStream.toByteArray();
        }
    }  

    
}