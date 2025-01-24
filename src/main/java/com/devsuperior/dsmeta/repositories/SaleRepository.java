package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ContentDTO;
import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true, value = """
                SELECT
                    s.id AS id,
                    s.date AS date,
                    s.amount AS amount,
                    se.name AS sellerName
                FROM tb_sales s
                JOIN tb_seller se ON s.seller_id = se.id
                WHERE s.date BETWEEN :minDate AND :maxDate
                AND LOWER(se.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    List<Object[]> searchReport(String minDate, String maxDate, String name);
}
