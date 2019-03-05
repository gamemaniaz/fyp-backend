package foodbank.reporting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.reporting.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
	Invoice findById(Long id);
	
	Invoice findByInvoiceLabel(String invoiceId);

}
