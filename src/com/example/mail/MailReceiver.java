package com.example.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * ÿ���յ����ʼ� ��һ��ReciveMail����
 * 
 **/

public class MailReceiver {

	private MimeMessage mineMsg = null;
	private StringBuffer mailContent = new StringBuffer();// �ʼ�����
	private String dataFormat = "yy-MM-dd HH:mm";// Ĭ�ϵ�ʱ���ʽ

	/**
	 * ���캯��
	 * 
	 * @param mimeMessage
	 */

	public MailReceiver(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	// MimeMessage�趨
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	/**
	 * ��������˵��������ʼ���ַ
	 * 
	 * @throws MessagingException
	 */

	public String getFrom() throws MessagingException {

		InternetAddress address[] = (InternetAddress[]) mineMsg.getFrom();
		String addr = address[0].getAddress();
		String name = address[0].getPersonal();

		if (addr == null) {
			addr = "";
		}
		if (name == null) {
			name = "";
		}
		String nameAddr = name + "<" + addr + ">";
		return nameAddr;
	}

	/**
	 * �������ͣ���ȡ�ʼ���ַ "TO"--�ռ��˵�ַ "CC"--�����˵�ַ "BCC"--�����˵�ַ
	 * 
	 */
	public String getMailAddress(String Type) throws Exception {
		String mailAddr = "";
		String addType = Type.toUpperCase();
		InternetAddress[] address = null;
		// �õ��ռ���/������/�����˵�ַ
		if (addType.equals("TO")) {
			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.TO);
		} else if (addType.equals("CC")) {
			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.CC);
		} else if (addType.equals("BBC")) {
			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.BCC);

		} else {
			System.out.println("error type!");
			throw new Exception("Error emailaddr type!");
		}

		if (address != null) {// ����ʼ���ַ��Ϊ��
			for (int i = 0; i < address.length; i++) {

				String mailaddress = address[i].getAddress();// 2.���������ԭ������Ҫ�����˽�
																// ,��ϸ�˽�"mailaddress"��"mailAddr"
																// getAddress()
				if (mailaddress != null) {
					mailaddress = MimeUtility.decodeText(mailaddress);// 3.MimeUtility.decodeText()���������ɣ�
				} else {
					mailaddress = "";
				}

				String name = address[i].getPersonal();
				if (name != null) {
					name = MimeUtility.decodeText(name);
				} else {
					name = " ";
				}
				mailAddr = name + "<" + mailaddress + ">";
			}

		}
		return mailAddr;

	}

	/**
	 * ȡ���ʼ�����
	 * 
	 * @return String
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */

	public String getSubject() throws UnsupportedEncodingException,
			MessagingException {
		String subject = "";
		subject = MimeUtility.decodeText(mineMsg.getSubject());
		if (subject == null) {
			subject = "";
		}
		return subject;
	}

	/**
	 * �趨�������ڸ�ʽ
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	/**
	 * ȡ���ʼ�����
	 * 
	 * @throws MessagingException
	 */
	public String getSentDate() throws MessagingException {
		Date sentdata = mineMsg.getSentDate();
		if (sentdata != null) {
			SimpleDateFormat format = new SimpleDateFormat(dataFormat);
			return format.format(sentdata);
		} else {
			return "�����";
		}
	}

	public void setMailContent(StringBuffer mailContent) {
		this.mailContent = mailContent;
	}

	/**
	 * ȡ���ʼ�����
	 * 
	 * @throws Exception
	 */
	public String getMailContent() throws Exception {
		compileMailContent((Part) mineMsg);
		return mailContent.toString();
	}

	/**
	 * �����ʼ�����
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	public void compileMailContent(Part part) throws MessagingException,
			IOException {
		String contentType = part.getContentType();// ��ȡ����
		int nameIndex = contentType.indexOf("name");// �õ���name��Ӧ�ĵ�nameIndex
		boolean connName = false;
		if (nameIndex != -1) {
			if (nameIndex != -1) {
				connName = true;
			}
			if (part.isMimeType("text/plain") && !connName) {
				mailContent.append(part.getContent().toString());
			} else if (part.isMimeType("text/html") && !connName) {
				mailContent.append(part.getContent().toString());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					compileMailContent(multipart.getBodyPart(i));
				}
			} else if (part.isMimeType("message/rfc822")) {
				compileMailContent((Part) part.getContent());
			}
		}

	}

	/**
	 * �Ƿ������ʼ�
	 * 
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mineMsg).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}
}
