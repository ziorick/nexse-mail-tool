package com.nexse.mail;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.io.StringWriter;
import java.util.Properties;

public class WelcomeMailSenderImpl {

    private final transient Logger log = LoggerFactory.getLogger(getClass());

    static {
        Properties p = new Properties();
        p.put("resource.loader", "class");
        p.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        try {
            Velocity.init(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void welcome(String uid, String name, String surname, String password, String email, String personalEmail) throws Exception {
        log.debug("welcome(): uid={}, name={}, surname={}, password={}, email={}, personalEmail={}", uid, name, surname, password, email, personalEmail);
        final VelocityContext vc = new VelocityContext();
        vc.put("uid", uid);
        vc.put("name", name);
        vc.put("email", email);
        vc.put("password", password);
        vc.put("surname", surname);

        final Template t = Velocity.getTemplate("welcome-mail.vm");
        final StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("System Administrator <sysadm@nexse.com>");
        mail.setSubject("Casella posta Nexse: " + name + ' ' + surname);
        mail.setTo(name + ' ' + surname + " <" + email + '>');
        mail.setCc(name + ' ' + surname + " <" + personalEmail + '>');
        mail.setBcc("Mirko Caserta <mirko.caserta@nexse.com>");
        mail.setText(sw.toString());
        mailSender.send(mail);
        log.debug("welcome(): mail sent");
    }

    public void welcomeNoPasswd(String name, String surname, String email, String personalEmail) throws Exception {
        log.debug("welcomeNoPasswd(): name={}, surname={}, email={}, personalEmail={}", name, surname, email, personalEmail);
        final VelocityContext vc = new VelocityContext();
        vc.put("name", name);
        vc.put("email", email);
        vc.put("surname", surname);
        vc.put("personalMail", personalEmail);

        final Template t = Velocity.getTemplate("welcome-mail-others.vm");
        final StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("System Administrator <sysadm@nexse.com>");
        mail.setSubject("Casella posta Nexse: " + name + ' ' + surname);
        mail.setTo("Mirko Caserta <mirko.caserta@nexse.com> ");
        mail.setCc(new String[]{
                "System Administrator <sysadm@nexse.com>",
                "Human Resources <hr@nexse.com>",
                "Leonardo Ambrosini <leonardo.ambrosini@nexse.com>",
                "Fabrizio Peroni <fabrizio.peroni@nexse.com>"
        });
        mail.setText(sw.toString());
        mailSender.send(mail);
        log.debug("welcome(): mail sent");
    }

}
