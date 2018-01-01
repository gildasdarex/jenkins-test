package com.pej.pojo;

import com.poiji.internal.annotation.ExcelCell;
import org.dozer.Mapping;

/**
 * Created by darextossa on 7/22/17.
 */
public class OdkAgent {
    @ExcelCell(0)
    @Mapping("numeroagent")
    public String ae;
    @ExcelCell(2)//no mapping
    @Mapping("nom")
    public String enqueteur;

    public OdkAgent() {
    }

    public String getAe() {
        return ae;
    }

    public void setAe(String ae) {
        this.ae = ae;
    }

    public String getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(String enqueteur) {
        this.enqueteur = enqueteur;
    }
}
