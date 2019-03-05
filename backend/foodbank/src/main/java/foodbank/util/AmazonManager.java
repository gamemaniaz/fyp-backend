package foodbank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Component
public class AmazonManager implements CommandLineRunner {

	private static AmazonS3 client;
	
	private static final String REPORT_BUCKET = "foodbank-production-reports";
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		// The credentials should not be stored on production
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
		client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.AP_SOUTHEAST_1)
					.build();
	}
	
	public static void generatePDFPageCounts(String filename) {
		client.putObject(REPORT_BUCKET, filename, new File(filename));
		S3Object pdf = client.getObject(REPORT_BUCKET, filename);
		S3ObjectInputStream stream = pdf.getObjectContent();
		try {
			PdfReader reader = new PdfReader(stream);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(filename));
			int numPages = reader.getNumberOfPages();
			for(int i = 1; i <= numPages; i++) {
				String footer = "Page " + i + " of " + numPages;
				ColumnText.showTextAligned(stamper.getOverContent(i), Element.ALIGN_RIGHT, new Phrase(footer, FontFactory.getFont(FontFactory.HELVETICA, 9)), 
						reader.getPageSize(i).getRight(36), reader.getPageSize(i).getBottom(16), 0);
			}
			stamper.close();
			reader.close();
			client.putObject(new PutObjectRequest(REPORT_BUCKET, filename, new File(filename)));
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static URL retrieveInvoiceURL(String invoiceNumber) {
		return client.generatePresignedUrl(new GeneratePresignedUrlRequest(REPORT_BUCKET, invoiceNumber + ".pdf"));
	}

}
