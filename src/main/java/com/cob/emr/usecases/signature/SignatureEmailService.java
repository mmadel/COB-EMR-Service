package com.cob.emr.usecases.signature;

import com.cob.emr.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SignatureEmailService {
    @Value("${app.signature.base-url}")
    private String baseUrl;

    @Autowired
    private EmailService emailService;

    public void sendSignatureLink(String email, String doctorName, String token) {
        String signatureUrl = baseUrl + "/emr-signature/capture?token=" + token;
        String subject = "Signature Request – Dr. " + doctorName;
        String htmlBody = buildSignatureEmailHtml(doctorName, signatureUrl);
        emailService.sendHtmlEmail(email, subject, htmlBody);
        log.info("Signature capture link sent to Dr. {} ({})", doctorName, email);
    }

    private String buildSignatureEmailHtml(String doctorName, String signatureUrl) {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head><meta charset=\"UTF-8\"></head>"
                + "<body style=\"margin:0;padding:0;background-color:#f4f6f9;font-family:Arial,Helvetica,sans-serif;\">"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#f4f6f9;padding:40px 0;\">"
                + "<tr><td align=\"center\">"
                + "<table role=\"presentation\" width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#ffffff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,0.08);\">"
                // Header
                + "<tr>"
                + "<td style=\"background-color:#1a73e8;padding:30px 40px;text-align:center;\">"
                + "<h1 style=\"margin:0;color:#ffffff;font-size:22px;font-weight:600;\">Signature Request</h1>"
                + "</td>"
                + "</tr>"
                // Body
                + "<tr>"
                + "<td style=\"padding:36px 40px;\">"
                + "<p style=\"margin:0 0 18px;font-size:16px;color:#333333;line-height:1.6;\">"
                + "Dear Dr. " + doctorName + ","
                + "</p>"
                + "<p style=\"margin:0 0 24px;font-size:15px;color:#555555;line-height:1.6;\">"
                + "We need your electronic signature. Please click the button below to open the signature capture page."
                + "</p>"
                // Button
                + "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0 auto 28px;\">"
                + "<tr><td align=\"center\" style=\"border-radius:6px;background-color:#1a73e8;\">"
                + "<a href=\"" + signatureUrl + "\" target=\"_blank\" "
                + "style=\"display:inline-block;padding:14px 36px;font-size:16px;color:#ffffff;text-decoration:none;font-weight:600;\">"
                + "Capture Signature"
                + "</a>"
                + "</td></tr>"
                + "</table>"
                + "<p style=\"margin:0 0 10px;font-size:13px;color:#888888;line-height:1.5;\">"
                + "If the button above does not work, copy and paste the following link into your browser:"
                + "</p>"
                + "<p style=\"margin:0 0 0;font-size:13px;color:#1a73e8;word-break:break-all;line-height:1.5;\">"
                + signatureUrl
                + "</p>"
                + "</td>"
                + "</tr>"
                // Footer
                + "<tr>"
                + "<td style=\"background-color:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #eeeeee;\">"
                + "<p style=\"margin:0;font-size:12px;color:#aaaaaa;line-height:1.5;\">"
                + "This is an automated message from COB EMR. Please do not reply to this email."
                + "</p>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td></tr>"
                + "</table>"
                + "</body>"
                + "</html>";
    }
}
