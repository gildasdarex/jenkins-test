package com.pej.web;

import com.pej.domains.Formateur;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;


/**
 * Created by darextossa on 7/23/17.
 */
public class FormateurFormatter implements Formatter<Formateur>{
    @Override
    public Formateur parse(String s, Locale locale) throws ParseException {
        Formateur formateur = new Formateur();
        formateur.setIdformateur(Integer.parseInt(s));
        return formateur;
    }

    @Override
    public String print(Formateur formateur, Locale locale) {
        return formateur.getIdformateur().toString();
    }
}
