package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BirtReportController {

    @GetMapping("/viewBirtReport")
    public RedirectView viewBirtReport(@RequestParam String reportName,
                                      @RequestParam(required = false) String param1,
                                      @RequestParam(required = false) String param2) {


    	
        StringBuilder birtViewerUrl = new StringBuilder("http://localhost:8080/birt-viewer/frameset?__report=");
        //birtViewerUrl.append(reportName);
        birtViewerUrl.append(getClass().getResource("/reports/" + reportName + ".rptdesign"));

        System.out.println("Enter=" + getClass().getResource("/reports/" + reportName + ".rptdesign"));
        
        birtViewerUrl.append("&SSO_NO=").append("1001");
        birtViewerUrl.append("&SECTION_TYPE=").append("33");
        birtViewerUrl.append("&RECEIPT_DATE=").append("2023-03-09");
        birtViewerUrl.append("&ReportCode=").append("R5101");
        birtViewerUrl.append("&UserName=").append("Quko");
        
        /*
        if (param1 != null) {
            birtViewerUrl.append("&param1=").append(param1);
        }
        if (param2 != null) {
            birtViewerUrl.append("&param2=").append(param2);
        }
        */

        return new RedirectView(birtViewerUrl.toString());
    }
}