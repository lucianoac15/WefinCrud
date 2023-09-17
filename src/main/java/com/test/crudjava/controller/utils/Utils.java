package com.test.crudjava.controller.utils;

public class Utils {

    public static boolean validarIdentificacaoEstudante(String identificacao) {
        if (!identificacao.matches("(\\d{8})")) {
            return false;
        }

        int primeiroDigito = Character.getNumericValue(identificacao.charAt(0));
        int ultimoDigito = Character.getNumericValue(identificacao.charAt(7));

        if ((primeiroDigito + ultimoDigito) != 9)
            return false;
        return true;
    }

    public static boolean validarIdentificacaoAposentado(String identificacao) {
        if (!identificacao.matches("(\\d{10})")) {
            return false;
        }

        Character ultimoDigito = identificacao.charAt(9);

        for (int i = 0; i < 8; i++) {
            if (ultimoDigito == identificacao.charAt(i))
                return false;
        }

        return true;
    }

    public static boolean validarIdentificacaoCpf(String cpf) {

        if (!cpf.matches("(\\d{11})")) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) {
            firstDigit = 0;
        }

        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) {
            secondDigit = 0;
        }

        if (Character.getNumericValue(cpf.charAt(10)) != secondDigit) {
            return false;
        }

        return true;
    }

    public static boolean validarIdentificacaoCnpj(String cnpj) {
        if (!cnpj.matches("(\\d{14})")) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int sum = 0;
        int weight = 2;
        for (int i = 11; i >= 0; i--) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weight;
            weight = (weight + 1) % 10 == 0 ? 2 : weight + 1;
        }
        int firstDigit = (sum % 11) < 2 ? 0 : 11 - (sum % 11);

        if (Character.getNumericValue(cnpj.charAt(12)) != firstDigit) {
            return false;
        }

        sum = 0;
        weight = 2;
        for (int i = 12; i >= 0; i--) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weight;
            weight = (weight + 1) % 10 == 0 ? 2 : weight + 1;
        }
        int secondDigit = (sum % 11) < 2 ? 0 : 11 - (sum % 11);

        if (Character.getNumericValue(cnpj.charAt(13)) != secondDigit) {
            return false;
        }

        return true;
    }
}
