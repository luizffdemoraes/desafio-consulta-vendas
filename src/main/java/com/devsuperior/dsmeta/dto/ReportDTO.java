package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public class ReportDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String sellerName;

    // Construtor que será utilizado para mapear o resultado
    public ReportDTO(Long id, LocalDate date, Double amount, String sellerName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.sellerName = sellerName;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String toString() {
        return "ContentDTO{" +
                "id=" + id +
                ", date=" + date +
                ", amount=" + amount +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }
}

