package com.nexse.mail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static final String name = "Ivano";
    public static final String surname = "Rosi";
    public static final String uid = "ivano.rossi";
    public static final String domain = "nexse.com";
    //public static final String domain = "nexsesolutions.com";
    //public static final String domain = "w-lab.it";
    public static final String password = "U18Pn9al8LgW7iT2";
    public static final String personalEmail = "ivano.rossi@libero.it";

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        WelcomeMailSenderImpl mailer = ctx.getBean("welcomeMailSender", WelcomeMailSenderImpl.class);
        mailer.welcome(uid, name, surname, password, uid + '@' + domain, personalEmail);
        mailer.welcomeNoPasswd(name, surname, uid + '@' + domain, personalEmail);
    }

}
