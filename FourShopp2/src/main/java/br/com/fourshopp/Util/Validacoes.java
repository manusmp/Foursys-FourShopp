package br.com.fourshopp.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacoes {

    public static boolean isCpf(String cpf) {
        Integer multiply = 10;
        Integer totalSum = 0;
        Integer firstDigit;
        Integer lastDigit;
        if (cpf.length() != 11 || cpf == null || cpf.equals("") || cpf.equals("00000000000")
                || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666")
                || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false;
        }

        for (int i = 0; i < cpf.length() - 2; i++) {
            totalSum += Integer.parseInt(cpf.substring(i, i + 1)) * multiply;
            multiply--;
        }

        firstDigit = 11 - (totalSum % 11);
        multiply = 11;
        totalSum = 0;
        for (int i = 0; i < cpf.length() - 2; i++) {
            totalSum += Integer.parseInt(cpf.substring(i, i + 1)) * multiply;
            multiply--;
        }

        totalSum += firstDigit * 2;
        lastDigit = 11 - (totalSum % 11);
        return (firstDigit == Integer.parseInt(cpf.substring(9, 10))
                && lastDigit == Integer.parseInt(cpf.substring(10)));
    }

    public static boolean isSenha (String senha){
        if(senha.length() >= 8 && senha == null){
            return true;
        }
        return false;
    }

    public static boolean isCellPhone(String phone) {
        if (phone.equals("") || phone == null) {
            return false;
        }

        String rgx = "^([11-99]{2})([9]{1})([0-9]{8})+$";
        Pattern pattern = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isEmail(String email) {
        if (email.equals("") || email == null) {
            return false;
        }

        String regx = "^[a-zA-Z0-9_!#$%&'\\*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

