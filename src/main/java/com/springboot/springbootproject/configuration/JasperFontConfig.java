package com.springboot.springbootproject.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JasperFontConfig {

    @PostConstruct
    public void setupJasperFont() {
        System.setProperty("net.sf.jasperreports.default.font.name", "DejaVu Sans");
        System.setProperty("net.sf.jasperreports.default.pdf.font.name", "fonts/DejaVuSans.ttf");
        System.setProperty("net.sf.jasperreports.default.pdf.encoding", "Identity-H");
        System.setProperty("net.sf.jasperreports.default.pdf.embedded", "true");

        System.out.println("✅ JasperReports font configured: DejaVu Sans (Unicode ready)");
    }
}