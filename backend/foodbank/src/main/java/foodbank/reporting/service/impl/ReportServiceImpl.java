package foodbank.reporting.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.packing.entity.PackingList;
import foodbank.packing.repository.PackingRepository;
import foodbank.reporting.dto.InvoiceDTO;
import foodbank.reporting.entity.Invoice;
import foodbank.reporting.entity.InvoiceData;
import foodbank.reporting.entity.InvoiceLineItem;
import foodbank.reporting.repository.InvoiceRepository;
import foodbank.reporting.service.ReportService;
import foodbank.util.DateParser;
import foodbank.util.AmazonManager;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private PackingRepository packingRepository;
	
	@Override
	public List<InvoiceDTO> retrieveAllInvoices() {
		// TODO Auto-generated method stub
		List<Invoice> dbInvoices = invoiceRepository.findAll();
		List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();
		for(Invoice invoice : dbInvoices) {
			Beneficiary beneficiary = beneficiaryRepository.findOne(invoice.getBillingOrganizationId());
			invoices.add(new InvoiceDTO(invoice.getInvoiceLabel(), beneficiary.getUser().getName()));
		}
		return invoices;
	}

	@Override
	public InvoiceData retrieveInvoiceData(String invoiceId) {
		// TODO Auto-generated method stub
		Invoice invoice = invoiceRepository.findByInvoiceLabel(invoiceId);
		Beneficiary billingOrganization = beneficiaryRepository.findOne(invoice.getBillingOrganizationId());
		Beneficiary receivingOrganization = beneficiaryRepository.findOne(invoice.getReceivingOrganizationId());
		PackingList packingList = packingRepository.findById(invoice.getPackingListId());
		return new InvoiceData(invoice, billingOrganization, receivingOrganization, packingList);
	}

	@Override
	public void updateInvoiceData(Map<String, String> details) throws ParseException {
		// TODO Auto-generated method stub
		String invoiceId = details.get("invoiceNumber");
		String deliveryDate = details.get("deliveryDate");
		String deliveryTime = details.get("deliveryTime");
		String issuedBy = details.get("issuedBy");
		String comments = details.get("comments");
		boolean deliveryRequired = Boolean.valueOf(details.get("deliveryRequired"));
		Invoice dbInvoice = invoiceRepository.findByInvoiceLabel(invoiceId);
		if(dbInvoice != null) {
			dbInvoice.setDeliveryDate(DateParser.convertStringToDate(deliveryDate));
			dbInvoice.setDeliveryTime(DateParser.convertStringToTime(deliveryTime));
			dbInvoice.setIssuedBy(issuedBy);
			dbInvoice.setComments(comments);
			dbInvoice.setDeliveryStatus(deliveryRequired);
		}
		invoiceRepository.save(dbInvoice);
	}

	@Override
	public void generateInvoicePDF(String invoiceId) {
		// TODO Auto-generated method stub
		Document document = new Document(PageSize.A4);
		Invoice invoice = invoiceRepository.findByInvoiceLabel(invoiceId);
		Beneficiary billingOrganization = beneficiaryRepository.findOne(invoice.getBillingOrganizationId());
		Beneficiary receivingOrganization = beneficiaryRepository.findOne(invoice.getReceivingOrganizationId());
		PackingList packingList = packingRepository.findById(invoice.getPackingListId());
		InvoiceData invoiceData = new InvoiceData(invoice, billingOrganization, receivingOrganization, packingList);
		String pdfName = invoiceData.getInvoiceLabel() + ".pdf";
		try {
			PdfWriter.getInstance(document, new FileOutputStream(pdfName));
			document.open();
			document.addAuthor("The Food Bank Singapore");
			document.addTitle("Delivery Invoice");
			generateReportHeaders(document);
			//System.out.println("Report headers generated");
			Paragraph spacingParagraph = new Paragraph(8);
			spacingParagraph.add("\n");
			generateInvoiceDetailTable(document, invoiceData);
			//System.out.println("Invoice detail table generated");
			document.add(spacingParagraph);
			populateDeliveryInfo(document, invoiceData);
			//System.out.println("Delivery info populated");
			document.add(spacingParagraph);
			generateOrganizationHeaderTable(document);
			//System.out.println("Organization header generated");
			populateOrganizationDetails(document, invoiceData);
			//System.out.println("Organization details populated");
			document.add(spacingParagraph);
			populateItemDetails(document, invoiceData);
			//System.out.println("Item details populated");
			document.add(spacingParagraph);
			generateSignatureField(document, spacingParagraph);
			//System.out.println("Signature field generated");
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			document.close();
			AmazonManager.generatePDFPageCounts(pdfName);
			invoice.setGenerationStatus(true);
			invoiceRepository.save(invoice);
		}
	}
	
	private PdfPCell createHeaderImageCell(String path) throws DocumentException, IOException {
		Image image = Image.getInstance(path);
		PdfPCell cell = new PdfPCell(image, true);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	private PdfPCell createHeaderTextCell(String text) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		String[] textData = text.split("\n");
		Paragraph paragraph = new Paragraph(12);
		for(int i = 0; i < textData.length; i++) {
			String lineData = textData[i];
			if(lineData.contains("www")) {
				Chunk chunk = new Chunk(lineData + "\n", FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLUE));
				chunk.setUnderline(0.5f, -2);
				paragraph.add(chunk);
			} else {
				paragraph.add(new Chunk(lineData + "\n", FontFactory.getFont(FontFactory.HELVETICA, 9)));
			}
		}
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		cell.addElement(paragraph);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	private PdfPCell createInvoiceDetailTextCell(String text, boolean background) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		BaseColor color = new BaseColor(214, 227, 188);
		if(background) {
			cell.setBackgroundColor(color);
		}
		cell.addElement(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 9)));
		cell.setUseAscender(true);
		cell.setPadding(4);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}
	
	/*
	 * Remarks: Logo needs to be hosted
	 */
	private void generateReportHeaders(Document document) throws DocumentException, IOException {
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidthPercentage(100);
		headerTable.setWidths(new int[] {1, 2});
		headerTable.addCell(createHeaderImageCell("logo.jpg"));
		headerTable.addCell(createHeaderTextCell(Invoice.COMPANY_NAME + 
				"\n" + Invoice.ADDRESS_LINE_1 + 
				"\n" + Invoice.ADDRESS_LINE_2 +
				"\n" + Invoice.ADDRESS_LINE_3 + 
				"\n" + Invoice.COMPANY_WEBSITE + 
				"\n" + Invoice.CO_REG_NO));
		document.add(headerTable);
		document.add(new Paragraph());
		document.add(new Chunk(new LineSeparator()));
	}
	
	private void generateInvoiceDetailTable(Document document, InvoiceData invoiceData) throws DocumentException, IOException {
		PdfPTable invoiceDetailTable = new PdfPTable(2);
		invoiceDetailTable.setWidthPercentage(100);
		invoiceDetailTable.setWidths(new int[] {4, 3});
		populateDeliveryRequirements(invoiceDetailTable, invoiceData);
		populateInvoiceDetails(invoiceDetailTable, invoiceData);
		document.add(invoiceDetailTable);
	}
	
	/*
	 * Remarks: Wingdings Font needs to be hosted
	 */
	private void populateDeliveryRequirements(PdfPTable invoiceDetailTable, InvoiceData invoiceData) throws DocumentException, IOException {
		BaseFont base = BaseFont.createFont("wingding.ttf", BaseFont.IDENTITY_H, false);
		Font font = new Font(base, 14f, Font.NORMAL);
		char checked ='\u00FE';
		char unchecked ='\u00A8';
		PdfPCell leftCell = new PdfPCell();
		leftCell.addElement(new Paragraph("INVOICE (Donation)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28)));
		Phrase deliveryStatus = new Phrase();
		boolean deliveryRequired = invoiceData.isDeliveryRequired();
		char deliveryChar = checked;
		char collectionChar = unchecked;
		if(!deliveryRequired) { 
			deliveryChar = unchecked; 
			collectionChar = checked; 
		}
		deliveryStatus.add(new Chunk(String.valueOf(deliveryChar), font));
		deliveryStatus.add(" Deliver ");
		deliveryStatus.add(new Chunk(String.valueOf(collectionChar), font));
		deliveryStatus.add(" Self-Collect");
		leftCell.addElement(deliveryStatus);
		leftCell.setUseAscender(true);
		leftCell.setHorizontalAlignment(Element.ALIGN_TOP);
		leftCell.setBorder(Rectangle.NO_BORDER);
		invoiceDetailTable.addCell(leftCell);
	}
	
	private void populateInvoiceDetails(PdfPTable invoiceDetailTable, InvoiceData invoiceData) throws DocumentException, IOException {
		PdfPTable innerTable = new PdfPTable(2);
		innerTable.setWidthPercentage(100);
		innerTable.setWidths(new int[] {2, 3});
		innerTable.addCell(createInvoiceDetailTextCell("Invoice No.", true));
		innerTable.addCell(createInvoiceDetailTextCell(invoiceData.getInvoiceLabel(), false));
		innerTable.addCell(createInvoiceDetailTextCell("Invoice Date", true));
		innerTable.addCell(createInvoiceDetailTextCell(invoiceData.getPackedDate(), false));
		innerTable.addCell(createInvoiceDetailTextCell("Purchase Order No.", true));
		innerTable.addCell(createInvoiceDetailTextCell("", false));
		invoiceDetailTable.addCell(new PdfPCell(innerTable));
	}
	
	private void populateDeliveryInfo(Document document, InvoiceData invoiceData) throws DocumentException, IOException {
		PdfPTable deliveryInfoTable = new PdfPTable(4);
		deliveryInfoTable.setWidthPercentage(100);
		deliveryInfoTable.setWidths(new int[] {1, 1, 1, 1});
		deliveryInfoTable.addCell(createInvoiceDetailTextCell("Delivery Date", true));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell("Delivery Time", true));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell("Issued By", true));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell("Comments", true));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell(invoiceData.getDeliveryDate(), false));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell(invoiceData.getDeliveryTime(), false));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell(invoiceData.getIssuedBy(), false));
		deliveryInfoTable.addCell(createInvoiceDetailTextCell(invoiceData.getComments(), false));
		document.add(deliveryInfoTable);
	}
	
	private void generateOrganizationHeaderTable(Document document) throws DocumentException, IOException {
		PdfPTable organizationHeaderTable = new PdfPTable(3);
		organizationHeaderTable.setWidthPercentage(100);
		organizationHeaderTable.setWidths(new int[] {30, 1, 30});
		organizationHeaderTable.addCell(createInvoiceDetailTextCell("Bill To:", true));
		PdfPCell spacingCell = new PdfPCell();
		spacingCell.setBorder(Rectangle.NO_BORDER);
		organizationHeaderTable.addCell(spacingCell);
		organizationHeaderTable.addCell(createInvoiceDetailTextCell("Deliver To:", true));
		document.add(organizationHeaderTable);
		
	}
	
	private void populateOrganizationDetails(Document document, InvoiceData invoiceData) throws DocumentException, IOException {
		PdfPTable organizationDetailTable = new PdfPTable(5);
		PdfPCell spacingCell = new PdfPCell();
		spacingCell.setBorder(Rectangle.NO_BORDER);
		organizationDetailTable.setWidthPercentage(100);
		organizationDetailTable.setWidths(new int[] {9, 22, 1, 9, 22});
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Organization:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getOrganizationToBill(), false));
		organizationDetailTable.addCell(spacingCell);
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Organization:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getReceivingOrganization(), false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Address:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getOrganizationToBillAddress(), false));
		organizationDetailTable.addCell(spacingCell);
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Address:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getReceivingOrganizationAddress(), false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact Person:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getOrganizationToBillContactPerson(), false));
		organizationDetailTable.addCell(spacingCell);
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact Person:", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getReceivingOrganizationContactPerson(), false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact No", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getOrganizationToBillContactNumber(), false));
		organizationDetailTable.addCell(spacingCell);
		organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact No", false));
		organizationDetailTable.addCell(createInvoiceDetailTextCell(invoiceData.getReceivingOrganizationContactNumber(), false));
		document.add(organizationDetailTable);
	}
	
	private void populateItemDetails(Document document, InvoiceData invoiceData) throws DocumentException, IOException {
		PdfPTable itemDetailTable = new PdfPTable(8);
		itemDetailTable.setWidthPercentage(100);
		itemDetailTable.setWidths(new int[] {2, 14, 6, 10, 10, 3, 4, 4});
		itemDetailTable.addCell(createInvoiceDetailTextCell("No", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Item No.", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Category", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Classification", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Description", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Qty", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Value", true));
		itemDetailTable.addCell(createInvoiceDetailTextCell("Total", true));
		List<InvoiceLineItem> invoiceLineItems = invoiceData.getInvoiceLineItem();
		double combinedTotalValue = invoiceData.getCombinedTotalValue();
		for(int i = 0; i < invoiceLineItems.size(); i++) {
			InvoiceLineItem invoiceLineItem = invoiceLineItems.get(i);
			itemDetailTable.addCell(createInvoiceDetailTextCell(String.valueOf(i+1), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(invoiceLineItem.getItemNo(), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(invoiceLineItem.getCategory(), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(invoiceLineItem.getClassification(), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(formatDescription(invoiceLineItem.getDescription()), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(String.valueOf(invoiceLineItem.getQuantity()), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(formatValue(invoiceLineItem.getValue()), false));
			itemDetailTable.addCell(createInvoiceDetailTextCell(formatValue(invoiceLineItem.getTotalValue()), false));
		}
		document.add(itemDetailTable);
		PdfPTable itemRemarksTable = new PdfPTable(3);
		itemRemarksTable.setWidthPercentage(100);
		itemRemarksTable.setWidths(new int[] {46, 6, 6});
		itemRemarksTable.addCell(createInvoiceDetailTextCell("Remarks:", false));
		itemRemarksTable.addCell(createInvoiceDetailTextCell("TOTAL:", true));
		itemRemarksTable.addCell(createInvoiceDetailTextCell(formatValue(combinedTotalValue), true));
		document.add(itemRemarksTable);
	}
	
	private String formatDescription(String description) {
		String[] descriptionArray = description.split("-");
		String itemName = descriptionArray[0];
		String weight = descriptionArray[1];
		String halalStatus = "";
		if(descriptionArray.length == 3) {
			halalStatus = ", " + descriptionArray[2];
		}
		return itemName + " (" + weight + halalStatus + ")";
	}
	
	private String formatValue(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("$#.00");
		return decimalFormat.format(value);
	}
	
	private void generateSignatureField(Document document, Paragraph spacingParagraph) throws DocumentException {
		Paragraph acknowledgementParagraph = new Paragraph();
		Chunk acknowledgementChunk = new Chunk("WE ACKNOWLEDGE THAT THE ITEMS ARE RECEIVED IN GOOD CONDITION.", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));
		acknowledgementParagraph.add(acknowledgementChunk);
		acknowledgementParagraph.setAlignment(Element.ALIGN_CENTER);
		document.add(acknowledgementParagraph);
		Paragraph endingParagraphSpacing = new Paragraph(46);
		document.add(endingParagraphSpacing);
		document.add(new Chunk(new LineSeparator(1, 25, BaseColor.BLACK, Element.ALIGN_LEFT, 0)));
		Font signatureFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
		document.add(new Paragraph("ACKNOWLEDGED BY", signatureFont));
		document.add(spacingParagraph);
		//document.add(new Paragraph("NAME:", signatureFont));
		document.add(new Paragraph("DATE:", signatureFont));
	}

}
