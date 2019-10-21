package com.docaposte.toubkal.b2.model;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.number.CurrencyStyleFormatter;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.number.PercentStyleFormatter;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public  class Type000 {

        @NumberFormat(pattern = "000000.000")
        private int typeEnregistrement;

        private String typeEmetteur;                            //"TP""TE""SI""OT"
    @NumberFormat(pattern = "000000.000")
        private int numEmetteur;                        // start="6" end="19" presence="O" type="N";

        private String blanc;                                   // start="20" end="25" presence="O" type="A";

        private String TypeDest;                    // start="26" end="27" presence="O" type="A";
    @NumberFormat(pattern = "000000.000")
        private int numDest;                       // start="28" end="41" presence="O" type="A";

        private String dateReceptionFluxFrontal;// start="42" end="47" type="N" presence="O";

        private String application;                             // start="48" end="49" type="A" presence="O";

        private String idFichier;               // start="50" end="55" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private int dateCreationFichier;             // start="56" end="61" type="N" presence="O";

        private String norme;                                   // start="62" end="65" type="A" presence="O" saveas="norme";

        private String versionNorme;                     // start="66" end="67" type="A" valeur="B2" presence="O";

        private String blanc2;                                   // start="68" end="68" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private String blanc3;                                   // start="69" end="69" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private String blanc6;                     // start="70" end="72" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private String blanc4;                                   // start="73" end="82" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private int longueurEnregistrement;                // start="83" end="85" type="N" presence="O";

        private String blanc5;                                   // start="86" end="91" type="A" presence="O";
    @NumberFormat(pattern = "000000.000")
        private String zoneMessage;                            // start="92" end="128" type="A" presence="O";

    @Override
    public String toString() {
        return  typeEnregistrement +
                typeEmetteur   +
                numEmetteur +
                blanc   +
                TypeDest +
                numDest  +
                dateReceptionFluxFrontal +
                application   +
                idFichier   +
                dateCreationFichier +
                norme  +
                versionNorme   +
                blanc2 +
                blanc3 +
                blanc6 +
                blanc4 +
                longueurEnregistrement +
                blanc5 +
                zoneMessage ;
    }

    public int getTypeEnregistrement() {
        return typeEnregistrement;
    }

    public void setTypeEnregistrement(int typeEnregistrement) {
        this.typeEnregistrement = typeEnregistrement;
    }

    public String getTypeEmetteur() {
        return typeEmetteur;
    }

    public void setTypeEmetteur(String typeEmetteur) {
        this.typeEmetteur = typeEmetteur;
    }

    public int getNumEmetteur() {
        return numEmetteur;
    }

    public void setNumEmetteur(int numEmetteur) {
        this.numEmetteur = numEmetteur;
    }

    public String getBlanc() {
        return blanc;
    }

    public void setBlanc(String blanc) {
        this.blanc = blanc;
    }

    public String getTypeDest() {
        return TypeDest;
    }

    public void setTypeDest(String typeDest) {
        TypeDest = typeDest;
    }

    public int getNumDest() {
        return numDest;
    }

    public void setNumDest(int numDest) {
        this.numDest = numDest;
    }

    public String getDateReceptionFluxFrontal() {
        return dateReceptionFluxFrontal;
    }

    public void setDateReceptionFluxFrontal(String dateReceptionFluxFrontal) {
        this.dateReceptionFluxFrontal = dateReceptionFluxFrontal;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(String idFichier) {
        this.idFichier = idFichier;
    }

    public int getDateCreationFichier() {
        return dateCreationFichier;
    }

    public void setDateCreationFichier(int dateCreationFichier) {
        this.dateCreationFichier = dateCreationFichier;
    }

    public String getNorme() {
        return norme;
    }

    public void setNorme(String norme) {
        this.norme = norme;
    }

    public String getVersionNorme() {
        return versionNorme;
    }

    public void setVersionNorme(String versionNorme) {
        this.versionNorme = versionNorme;
    }

    public String getBlanc2() {
        return blanc2;
    }

    public void setBlanc2(String blanc2) {
        this.blanc2 = blanc2;
    }

    public String getBlanc3() {
        return blanc3;
    }

    public void setBlanc3(String blanc3) {
        this.blanc3 = blanc3;
    }

    public String getBlanc6() {
        return blanc6;
    }

    public void setBlanc6(String blanc6) {
        this.blanc6 = blanc6;
    }

    public String getBlanc4() {
        return blanc4;
    }

    public void setBlanc4(String blanc4) {
        this.blanc4 = blanc4;
    }

    public int getLongueurEnregistrement() {
        return longueurEnregistrement;
    }

    public void setLongueurEnregistrement(int longueurEnregistrement) {
        this.longueurEnregistrement = longueurEnregistrement;
    }

    public String getBlanc5() {
        return blanc5;
    }

    public void setBlanc5(String blanc5) {
        this.blanc5 = blanc5;
    }

    public String getZoneMessage() {
        return zoneMessage;
    }

    public void setZoneMessage(String zoneMessage) {
        this.zoneMessage = zoneMessage;
    }


}