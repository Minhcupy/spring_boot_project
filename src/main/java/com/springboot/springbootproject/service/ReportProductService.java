package com.springboot.springbootproject.service;

import java.io.FileNotFoundException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ReportProductService {
    JasperPrint generateReport() throws FileNotFoundException, JRException;
}
