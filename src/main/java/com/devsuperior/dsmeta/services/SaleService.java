package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        return repository.findById(id)
                .map(SaleMinDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado para o ID.: " + id));
    }

    public Page<ReportDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) {
        // Data atual do sistema
        LocalDate today = getToday();

        // Validar e processar maxDate
        LocalDate validMaxDate = getMaxDate(maxDate, today);

        // Validar e processar minDate
        LocalDate validMinDate = getMinDate(minDate, validMaxDate);

        return repository.searchReport(validMinDate, validMaxDate, name, pageable);
    }

    public List<SummaryDTO> summaryReport(String minDate, String maxDate) {
        // Data atual do sistema
        LocalDate today = getToday();

        // Validar e processar maxDate
        LocalDate validMaxDate = getMaxDate(maxDate, today);

        // Validar e processar minDate
        LocalDate validMinDate = getMinDate(minDate, validMaxDate);

        return repository.summaryReport(validMinDate, validMaxDate);
    }

    private static LocalDate getToday() {
        return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
    }

    private static LocalDate getMinDate(String minDate, LocalDate validMaxDate) {
        return getMaxDate(minDate, (validMaxDate.minusYears(1L)));
    }

    private static LocalDate getMaxDate(String maxDate, LocalDate today) {
        return (maxDate == null || maxDate.isBlank())
                ? today
                : LocalDate.parse(maxDate);
    }
}
