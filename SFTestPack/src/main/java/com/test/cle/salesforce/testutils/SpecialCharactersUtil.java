package com.test.cle.salesforce.testutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialCharactersUtil {

    private SpecialCharactersUtil() {
    }

    // !@#$%^&*
    public static boolean isExist(String source) {

        Pattern regex = Pattern.compile("^[a-zA-Z_0-9_äöüÄÖÜùûüÿàâæéèêëïîôœÙÛÜŸÀÂÆÉÈÊËÏÎÔŒ' ]*$");
        Matcher matcher = regex.matcher(source);

        return !matcher.matches();
    }

    public static void main(String[] argds) {

        String source = "äöüÄÖÜ";
        SpecialCharactersUtil.isExist(source);
    }


    public static String capitalizeFirstLetter(String str) {
        return str.length() == 0 ? str
                : str.length() == 1 ? str.toUpperCase()
                : str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
