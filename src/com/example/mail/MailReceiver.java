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
 * 每封收到的邮件 是一个ReciveMail对象
 * 
 **/

public class MailReceiver {

	private MimeMessage mineMsg = null;
	private StringBuffer mailContent = new StringBuffer();// 邮件内容
	private String dataFormat = "yy-MM-dd HH:mm";// 默认的时间格式

	/**
	 * 构造函数
	 * 
	 * @param mimeMessage
	 */

	public MailReceiver(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	// MimeMessage设定
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	/**
	 * 获得送信人的姓名和邮件地址
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
	 * 根据类型，获取邮件地址 "TO"--收件人地址 "CC"--抄送人地址 "BCC"--密送人地址
	 * 
	 */
	public String getMailAddress(String Type) throws Exception {
		String mailAddr = "";
		String addType = Type.toUpperCase();
		InternetAddress[] address = null;
		// 得到收件人/抄送人/密送人地址
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

		if (address != null) {// 如果邮件地址不为空
			for (int i = 0; i < address.length; i++) {

				String mailaddress = address[i].getAddress();// 2.由于网络的原因我需要上网了解
																// ,详细了解"mailaddress"和"mailAddr"
																// getAddress()
				if (mailaddress != null) {
					mailaddress = MimeUtility.decodeText(mailaddress);// 3.MimeUtility.decodeText()上网搜索吧！
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
	 * 取得邮件标题
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
	 * 设定收信日期格式
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	/**
	 * 取得邮件日期
	 * 
	 * @throws MessagingException
	 */
	public String getSentDate() throws MessagingException {
		Date sentdata = mineMsg.getSentDate();
		if (sentdata != null) {
			SimpleDateFormat format = new SimpleDateFormat(dataFormat);
			return format.format(sentdata);
		} else {
			return "不清楚";
		}
	}

	public void setMailContent(StringBuffer mailContent) {
		this.mailContent = mailContent;
	}

	/**
	 * 取得邮件内容
	 * 
	 * @throws Exception
	 */
	public String getMailContent() throws Exception {
		compileMailContent((Part) mineMsg);
		return mailContent.toString();
	}

	/**
	 * 解析邮件内容
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	public void compileMailContent(Part part) throws MessagingException,
			IOException {
		String contentType = part.getContentType();// 获取类型
		int nameIndex = contentType.indexOf("name");// 得到和name对应的的nameIndex
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
	 * 是否是新邮件
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
