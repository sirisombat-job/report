package com.example.demo.controller;

import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

import com.example.demo.model.Book;
//import com.example.demo.service.BirtReportService;
import com.example.demo.service.BookService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ExController {
	 	@Autowired
	    private BookService bookService;
	 	
	    @GetMapping("/")
	    public String home() {
	        return "Welcome to the Book API!";
	    }

	    @GetMapping("/findbyid/{id}")
	    public Book findBookById(@PathVariable int id) {
	        return bookService.findBookById(id);
	    }

	    @GetMapping("/findall")
	    public List<Book> findAllBooks() {
	        return bookService.findAllBooks();
	    }

	    @DeleteMapping("/delete")
	    public String deleteAllBooks() {
	        bookService.deleteAllBooks();
	        return "All books have been deleted.";
	    }

	    @GetMapping("/reports")
	    public String reports() {
	        System.out.println("enter");
	    	return "Welcome to the report API!";
	        
	    }    
	    

	    @GetMapping("/reports/{reportName}/pdf")
	    public ResponseEntity<byte[]> getPdfReport(@PathVariable String reportName, @RequestParam Map<String, Object> params,  HttpServletResponse response) throws Exception {   	

	    	PDFRenderOption pdfOptions = new PDFRenderOption();
	        pdfOptions.setOutputFormat("pdf");    	
	        byte[] reportBytes = bookService.generateReport(reportName, params, pdfOptions, response);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDispositionFormData("attachment", reportName + ".pdf"); // For direct download
        
	        return ResponseEntity.ok().headers(headers).body(reportBytes);	    	
	    }
	    
	    
	    @GetMapping("/reports/{reportName}/html")
	    public ResponseEntity<byte[]> getHtmlReport(@PathVariable String reportName, @RequestParam Map<String, Object> params,  HttpServletResponse response) throws Exception {
	        HTMLRenderOption htmlOptions = new HTMLRenderOption();
	        byte[] reportBytes = bookService.generateReport(reportName, params, htmlOptions, response);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.TEXT_HTML);
	        return ResponseEntity.ok().headers(headers).body(reportBytes);
	    } 	   

}
