package com.github.qa.actions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
//import com.gnostice.pdfone.PdfDocument;


import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.testng.Assert;

import com.github.qa.pages.LandingPage;
import com.github.qa.utils.TestUtils;

/**
 * The Class BaseActions.
 *
 * @author Nischal Tyagi
 */
public class BaseActions {
	
	/** The Log. */
	private static Logger Log = LogManager.getLogger(LandingPage.class.getName());

	/**
	 * Verify status code.
	 *
	 * @param statuscode the status code
	 */
	public void verifyStatusCode(int statuscode) {
		Assert.assertEquals(statuscode, 200, "Status check failed status is:" + statuscode);
	}

	/**
	 * Verify.
	 *
	 * @param actual   the actual
	 * @param expected the expected
	 */
	public void verify(boolean actual, boolean expected) {
		Assert.assertEquals(actual, expected);
	}

	/**
	 * Verify response time.
	 *
	 * @param actualTime the actual time
	 */
	public void verifyResponseTime(long actualTime) {
		long expectedTime = TestUtils.convertStringToLong(TestUtils.getProperty("engineer.ai.maxTime"));
		Assert.assertEquals(actualTime < expectedTime, true);
	}

	/**
	 * Verify response body.
	 * @param actual the actual
	 * @param expected the expected
	 * @param description the description
	 */
	public void verifyResponseBody(String actual, String expected, String description) {
		Assert.assertEquals(actual, expected, description);

	}

	/**
	 * Verify response body.
	 *
	 * @param actual the actual
	 * @param expected the expected
	 * @param description the description
	 */
	public void verifyResponseBody(boolean actual, boolean expected, String description) {
		Assert.assertEquals(actual, expected, description);

	}

	/**
	 * Verify response body.
	 *
	 * @param actual the actual
	 * @param expected the expected
	 * @param descripton the description
	 */
	public void verifyResponseBody(int actual, int expected, String descripton) {
		Assert.assertEquals(actual, expected, descripton);

	}

	/**
	 * Verify.
	 *
	 * @param actual the actual
	 * @param expected the expected
	 * @param descripton the description
	 */
	public void verify(boolean actual, boolean expected, String descripton) {
		Assert.assertEquals(actual, expected, descripton);

	}
	
	public void verifyTwoListsAreSame(List<String> firstList, List<String> secondList, String descripton) {
		Assert.assertTrue(firstList.containsAll(secondList), descripton);

	}
	
	public  String  getEmailText(String email, String password, String subjectOfMail) throws Exception {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com", email, password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);

		System.out.println("Total Message:" + folder.getMessageCount());
		System.out.println("Unread Message:" + folder.getUnreadMessageCount());

		Message[] messages = null;
		boolean isMailFound = false;
		Message mailFromProx = null;
		String emailtext = null;

		// Search for mail from Prox with Subject = 'Email varification Testcase'
		for (int i = 0; i <= 8; i++) {

			messages = folder.search(new SubjectTerm(subjectOfMail), folder.getMessages());
			// Wait for 20 seconds
			if (messages.length == 0) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Search for unread mail
		// This is to avoid using the mail for which
		// Registration is already done
		for (Message mail : messages) {
			if (!mail.isSet(Flags.Flag.SEEN)) {
				mailFromProx = mail;
				Log.info("Message Count is: " + mailFromProx.getMessageNumber());
				isMailFound = true;
			}
		}

		// Test fails if no unread mail was found
		if (!isMailFound) {
			try {
				throw new Exception("Could not find new mail from iGotThis :-(");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Read the content of mail and get confirmation Token
		} else {
			String line;
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromProx.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			System.out.println(buffer);
			emailtext =  buffer.toString();
//			String result = buffer.toString().split("confirmation_token=")[1].split("CONFIRM")[0].replaceAll("[\">]","");
//			Log.info("Confirmation token is: "+ result);
		}
		return emailtext;
	}
	
	 public String getPdfTextLinewise(String path,String searchItem ) {
		 	String pdfFileText="",result="";
	        try (PDDocument document = PDDocument.load(new File(path))) {

	            document.getClass();

	            if (!document.isEncrypted()) {
				
	                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	                stripper.setSortByPosition(true);

									PDFTextStripper tStripper = new PDFTextStripper();
									pdfFileText = tStripper.getText(document);
									String lines[] = pdfFileText.split("\\r?\\n");
									for(String line:lines){
										if(line.contains(searchItem)){
											result=line.split(searchItem)[1];
											break;
										}
									
								}
	                
	                Log.info("Text in pdf:"+result);

	            }

	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return result;

	    }
	 
	 public String getCompletePdfText(String path) {
		 	String pdfFileText="";
	        try (PDDocument document = PDDocument.load(new File(path))) {

	            document.getClass();

	            if (!document.isEncrypted()) {
				
	                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	                stripper.setSortByPosition(true);

									PDFTextStripper tStripper = new PDFTextStripper();
									pdfFileText = tStripper.getText(document);
								
	                Log.info("Text in pdf:"+pdfFileText);

	            }

	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return pdfFileText;

	    }
	 
	 public boolean verifyPDFContent(String strURL, String reqTextInPDF) {
			
			boolean flag = false;
			
			PDFTextStripper pdfStripper = null;
			PDDocument pdDoc = null;
			COSDocument cosDoc = null;
			String parsedText = null;

			try {
				URL url = new URL(strURL);
				//File file = new File(new BufferedInputStream(url.openStream());
				
				BufferedInputStream file = new BufferedInputStream(url.openStream());
				PDFParser parser = new PDFParser( (RandomAccessRead) file);
				
				parser.parse();
				cosDoc = parser.getDocument();
				pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(1);
				
				pdDoc = new PDDocument(cosDoc);
				parsedText = pdfStripper.getText(pdDoc);
			} catch (MalformedURLException e2) {
				System.err.println("URL string could not be parsed "+e2.getMessage());
			} catch (IOException e) {
				System.err.println("Unable to open PDF Parser. " + e.getMessage());
				try {
					if (cosDoc != null)
						cosDoc.close();
					if (pdDoc != null)
						pdDoc.close();
				} catch (Exception e1) {
					e.printStackTrace();
				}
			}
			
			System.out.println("+++++++++++++++++");
			System.out.println(parsedText);
			System.out.println("+++++++++++++++++");

			if(parsedText.contains(reqTextInPDF)) {
				flag=true;
			}
			
			return flag;
		}
	 
	 public void getPdfContent(String url) throws IOException {

		    URL url1 =
		      new URL("http://www.gnostice.com/downloads/Gnostice_PathQuest.pdf");

		    byte[] ba1 = new byte[1024];
		    int baLength;
		    FileOutputStream fos1 = new FileOutputStream("download.pdf");

		    try {
		      // Contacting the URL
		      System.out.print("Connecting to " + url1.toString() + " ... ");
		      URLConnection urlConn = url1.openConnection();

		      // Checking whether the URL contains a PDF
		      if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
		          System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
		      } else {
		        try {

		          // Read the PDF from the URL and save to a local file
		          InputStream is1 = url1.openStream();
		          while ((baLength = is1.read(ba1)) != -1) {
		              fos1.write(ba1, 0, baLength);
		          }
		          fos1.flush();
		          fos1.close();
		          is1.close();

		          // Load the PDF document and display its page count
		          System.out.print("DONE.\nProcessing the PDF ... ");
		       //   PdfDocument doc = new PdfDocument();
		          try {
//		            doc.load("download.pdf");
		         //   System.out.println("DONE.\nNumber of pages in the PDF is " +
		       //                        doc.getPageCount());
	//	            doc.close();
		          } catch (Exception e) {
		            System.out.println("FAILED.\n[" + e.getMessage() + "]");
		          }

		        } catch (ConnectException ce) {
		          System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
		        }
		      }

		    } catch (NullPointerException npe) {
		      System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
		    }
		  }

}
