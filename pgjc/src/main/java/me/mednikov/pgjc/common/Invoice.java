package me.mednikov.pgjc.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Invoice implements Comparable<Invoice> {
    
    private final Integer invoiceId;
    private final LocalDate issuedDate;
    private final Customer customer;
    private final BigDecimal total;

    public Invoice(Integer invoiceId, LocalDate issuedDate, Customer customer, BigDecimal total) {
        this.invoiceId = invoiceId;
        this.issuedDate = issuedDate;
        this.customer = customer;
        this.total = total;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.invoiceId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Invoice o) {
        return this.getIssuedDate().compareTo(o.getIssuedDate());
    }
    
    
}
