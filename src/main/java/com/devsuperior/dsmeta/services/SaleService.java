package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ContentDTO;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

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
        // Data atual do sistema
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

        // Validar e processar maxDate
        String validMaxDate = (maxDate == null || maxDate.isBlank())
                ? today.toString()
                : LocalDate.parse(maxDate).toString();

        // Validar e processar minDate
        String validMinDate = (minDate == null || minDate.isBlank())
                ? LocalDate.parse(validMaxDate).minusYears(1L).toString()
                : LocalDate.parse(minDate).toString();

        List<Object[]> result = repository.searchReport(validMinDate, validMaxDate, name);

        List<ContentDTO> contents = result.stream()
                .map(row -> new ContentDTO(
                        ((BigInteger) row[0]).longValue(),      // id
                        ((java.sql.Date) row[1]).toLocalDate(), // date
                        (Double) row[2],                        // amount
                        (String) row[3]                         // sellerName
                ))
                .collect(Collectors.toList());

        return new ReportDTO(contents);
    }

    public List<SummaryDTO> summaryReport(String minDate, String maxDate) {
        // Data atual do sistema
        LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

        // Validar e processar maxDate
        String validMaxDate = (maxDate == null || maxDate.isBlank())
                ? today.toString()
                : LocalDate.parse(maxDate).toString();

        // Validar e processar minDate
        String validMinDate = (minDate == null || minDate.isBlank())
                ? LocalDate.parse(validMaxDate).minusYears(1L).toString()
                : LocalDate.parse(minDate).toString();

        List<Object[]> result = repository.summaryReport(validMinDate, validMaxDate);

        return result.stream()
                .map(row -> new SummaryDTO(
                        (String) row[0],                    // sellerName
                        ((BigDecimal) row[1]).doubleValue() // total
                ))
                .collect(Collectors.toList());
    }
}
