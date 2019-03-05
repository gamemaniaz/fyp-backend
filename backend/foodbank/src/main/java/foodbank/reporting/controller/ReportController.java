package foodbank.reporting.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodbank.reporting.dto.InvoiceDTO;
import foodbank.reporting.entity.InvoiceData;
import foodbank.reporting.service.ReportService;
import foodbank.util.AmazonManager;
import foodbank.util.MessageConstants;
import foodbank.util.ResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/rest/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/display-all")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO getAllReports() {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.INVOICE_RETRIEVE_SUCCESS);
		try {
			List<InvoiceDTO> invoices = reportService.retrieveAllInvoices();
			responseDTO.setResult(invoices);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/display")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO retrieveInvoiceData(@RequestParam(value="invoiceNumber", required = true) String invoiceNumber) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.INVOICE_DATA_RETRIEVE_SUCCESS);
		try {
			InvoiceData invoiceData = reportService.retrieveInvoiceData(invoiceNumber);
			responseDTO.setResult(invoiceData);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
		}
		return responseDTO;
	}
	
	@GetMapping("/retrieve-invoice")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO retrievePdf(@RequestParam(value="invoiceNumber", required = true) String invoiceNumber) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.INVOICE_DOWNLOAD_LINK_GENERATE_SUCCESS);
		try {
			URL downloadUrl = AmazonManager.retrieveInvoiceURL(invoiceNumber);
			responseDTO.setResult(downloadUrl);
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
		}
		return responseDTO;
	}
	
	@PostMapping("/generate-invoice")
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseDTO generateInvoice(@RequestBody Map<String, String> invoiceData) {
		ResponseDTO responseDTO = new ResponseDTO(ResponseDTO.Status.SUCCESS, null, MessageConstants.INVOICE_GENERATE_SUCCESS);
		try {
			reportService.updateInvoiceData(invoiceData);
			reportService.generateInvoicePDF(invoiceData.get("invoiceNumber"));
		} catch (Exception e) {
			responseDTO.setMessage(e.getMessage());
			responseDTO.setStatus(ResponseDTO.Status.FAIL);
		}
		return responseDTO;
	}

}
