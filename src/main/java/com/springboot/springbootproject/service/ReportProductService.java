package com.springboot.springbootproject.service;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportProductService {
    JasperPrint generateReport() throws FileNotFoundException, JRException;
}
