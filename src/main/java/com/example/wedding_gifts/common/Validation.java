package com.example.wedding_gifts.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

public final class Validation {

    private static String regexEmail = "([a-z0-9._]{3,64})(\\@)(gmail|hotmail|outlook)(\\.com)";
    private static String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$%^&-])(?=\\S+$).{8,80}$";
    private static String regexPixKey = "(([a-z0-9._]{3,64})(\\@)(gmail|hotmail|outlook)(\\.com))|(\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2})|(\\+\\d{1,3}\\d{2,3}\\d{8,9})|(\\d{14})|(([a-z0-9]){8}\\-([a-z0-9]){4}\\-([a-z0-9]){4}\\-([a-z0-9]){4}\\-\\d{12})";
    private static String regexDate = "([a-zA-Z]{3})\\ (((Jan|Mar|May|Jul|Aug|Oct|Dec)\\ (0[1-9]|1[0-9]|2[0-9]|3[0-1]))|((Apr|Jun|Sep|Nov)\\ (0[1-9]|1[0-9]|2[0-9]|3[0]))|((Feb)\\ (0[1-9]|1[0-9]|2[0-9])))\\ ([0-9]{2}\\:[0-9]{2}\\:[0-9]{2})\\ ([a-zA-Z0-9]{3})?\\ ?([0-9]{4})";
    private static String regexName = "[A-ZÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð][a-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšž,.'-]{2,13}(\\ [A-ZÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð][a-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšž,.'-]{2,13})?";
    private static String regexBrideGroom = "[a-zA-Z0-9_-]{3,25}";
    private static String regexTitle = "[\\S\\s]{5,30}";
    private static String regexDescription = "[\\S\\s]{10,300}";
    private static String regexCpf = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}";
    private static String regexCnpj = "\\d{14}";

    public static boolean email(String email) {
        Pattern pattern = Pattern.compile(regexEmail);
        return pattern.matcher(email).find();
    }

    public static boolean password(String password) {
        Pattern pattern = Pattern.compile(regexPassword);
        return pattern.matcher(password).find();
    }

    public static boolean pixKey(String pixKey) {
        Pattern pattern = Pattern.compile(regexPixKey);
        return pattern.matcher(pixKey).find();
    }

    public static boolean date(Date date) {
        Pattern pattern = Pattern.compile(regexDate);
        return pattern.matcher(date.toString()).find() && date.after(new Date());
    }

    public static boolean name(String firstName, String lastName) {
        Pattern pattern = Pattern.compile(regexName);
        return pattern.matcher(firstName + " " + lastName).find();
    }

    public static boolean name(String firstName) {
        Pattern pattern = Pattern.compile(regexName);
        return pattern.matcher(firstName).find();
    }

    public static boolean brideGroom(String brideGroom) {
        Pattern pattern = Pattern.compile(regexBrideGroom);
        return pattern.matcher(brideGroom).find();
    }

    public static boolean title(String title) {
        Pattern pattern = Pattern.compile(regexTitle);
        return pattern.matcher(title).find();
    }

    public static boolean description(String description) {
        Pattern pattern = Pattern.compile(regexDescription);
        return pattern.matcher(description).find();
    }

    public static boolean price(BigDecimal price) {
        return 
            price.compareTo(new BigDecimal(10)) == 1 && 
            price.compareTo(new BigDecimal(20000)) == -1;
    }

    public static boolean cpf(String cpf) {
        Pattern pattern = Pattern.compile(regexCpf);
        return pattern.matcher(cpf).find();
    }

    public static boolean cnpj(String cnpj) {
        Pattern pattern = Pattern.compile(regexCnpj);
        return pattern.matcher(cnpj).find();
    }
    
}
