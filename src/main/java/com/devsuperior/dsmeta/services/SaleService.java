package com.devsuperior.dsmeta.services;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.ContentDTO;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        return repository.findById(id)
                .map(SaleMinDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado para o ID.: " + id));
    }

    public ReportDTO searchReport(String minDate, String maxDate, String name) {
        System.out.println(minDate + " " + maxDate + " " + name);
        List<Object[]> result = repository.searchReport(minDate, maxDate, name);

        List<ContentDTO> contents = result.stream()
                .map(row -> new ContentDTO(
                        ((BigInteger) row[0]).longValue(),  // id
                        ((java.sql.Date) row[1]).toLocalDate(), // date
                        (Double) row[2],                     // amount
                        (String) row[3]                      // sellerName
                ))
                .collect(Collectors.toList());

        System.out.println(contents);

        // Cria o ReportDTO usando o Builder
        return new ReportDTO(contents);
    }
}
