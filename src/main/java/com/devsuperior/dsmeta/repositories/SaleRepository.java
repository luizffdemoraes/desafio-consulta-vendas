package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
            SELECT new com.devsuperior.dsmeta.dto.ReportDTO(
                s.id, 
                s.date, 
                s.amount, 
                se.name
            )
            FROM Sale s
            JOIN s.seller se
            WHERE s.date BETWEEN :minDate AND :maxDate
            AND (:name IS NULL OR LOWER(se.name) LIKE LOWER(CONCAT('%', :name, '%')))
            """)
    Page<ReportDTO> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(nativeQuery = true, value = """
                SELECT
                    se.name AS sellerName,
                    SUM(s.amount) AS total
                FROM tb_sales s
                JOIN tb_seller se ON s.seller_id = se.id
                WHERE s.date BETWEEN :minDate AND :maxDate
                GROUP BY se.name
            """)
    List<Object[]> summaryReport(String minDate, String maxDate);
}
