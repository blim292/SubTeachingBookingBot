import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SSLEmail extends UserInfo{

  void sendEmail(String date) {

        final String bot_username = "${{ secrets.emailbot_username }}";
        final String bot_password = "${{ secrets.emailbot_password }}";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(bot_username, bot_password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(bot_username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("${{ secrets.emailrecipient_username }}"));
            message.setSubject("Available Sub Spot- " + date);
            message.setText("There is an available teaching spot on " + date);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
