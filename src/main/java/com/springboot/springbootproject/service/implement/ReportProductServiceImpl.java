package com.springboot.springbootproject.service.implement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.springboot.springbootproject.entity.Product;
import com.springboot.springbootproject.repository.ProductRepository;
import com.springboot.springbootproject.service.ReportProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportProductServiceImpl implements ReportProductService {

    ProductRepository productRepository;

    @Override
    public JasperPrint generateReport() throws FileNotFoundException, JRException {
        List<Product> products = productRepository.findAll();

        File file = ResourceUtils.getFile("classpath:productt.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Report");

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }
}
