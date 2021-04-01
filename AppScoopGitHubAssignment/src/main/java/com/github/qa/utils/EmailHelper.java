package com.github.qa.utils;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

public class EmailHelper {
	
	/** The Log. */
	private static Logger Log = LogManager.getLogger(DataUtils.class.getName());

	public EmailHelper() {
		Log.info("Initialized Email Helper Class");
	}


	public static void getConformationTokenFromEmailText(String email, String password, String subjectOfMail) throws Exception {
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

		// Search for mail from Prox with Subject = 'Email varification Testcase'
		for (int i = 0; i <= 5; i++) {

			messages = folder.search(new SubjectTerm("Confirm your email!"), folder.getMessages());
			// Wait for 20 seconds
			if (messages.length == 0) {
				try {
					Thread.sleep(3000);
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
			String result = buffer.toString().split("confirmation_token=")[1].split("CONFIRM")[0].replaceAll("[\">]","");
			Log.info("Confirmation token is: "+ result);
		}
	}
}