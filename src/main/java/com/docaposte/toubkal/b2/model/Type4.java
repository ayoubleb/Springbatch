package com.docaposte.toubkal.b2.model;

class Type_4 {
	

private String Type_denregistrement;                               //" start="1" end="1" valeur="4" presence="O" type="N"/>

private String N_partenaire_de_santé;                              //" start="2" end="10" valeur="" presence="O" type="N"/>

private String blanc;                                              //" start="11" end="11" valeur="" presence="F" type="A"/>

private String N_immatriculation_assuré;                           //" start="12" end="24" valeur="" presence="O" type="A"/>

private String Clé_du_N_immatriculation;                           //" start="25" end="26" valeur="" presence="O" type="N"/>

private String N_facture;                                          //" start="27" end="35" valeur="" presence="O" type="N"/>

private String Complément_de_type;                                 //" start="36" end="36" valeur="" presence="F" type="A"/>

private String Séquence;                                           //" start="37" end="38" valeur="" presence="F" type="N"/>

private String Mode_de_traitement;                                 //" start="39" end="40" valeur="" presence="F" type="N"/>

private String Discipline_de_prestations;                          //" start="41" end="43" valeur="" presence="F" type="N"/>

private String Date_de_la_demande_daccord_préalable_ou_date_daccord_de_prise_en_charge_CMU;                   //" start="44" end="49" valeur="" presence="F" type="N"/>

private Enum   Code_accord_préalable;  //0,4,9,5                      //" start="50" end="50" valeur="" presence="O" type="N">
 
private String  Séquence_de_renouvellement;                         //" start="51" end="52" valeur="" presence="F" type="A"/>

private String  Blanc;                                              //" start="53" end="53" valeur="" presence="F" type="A"/>

private String  Justification_dexonération_ou_de_modulation_du_ticket_modérateur_au_niveau_de_lacte;//" start="54" end="54" valeur="" presence="F" type="A"/>

private String N_exécutant_clé_ayant_effectué_lacte_ou_fourni_le_produit;                   //" start="55" end="63" valeur="" presence="O" type="N"/>

private String Zone_tarif_de_lexécutant;                             //" start="64" end="65" valeur="" presence="F" type="N"/>

private String Spécialité_de_lexécutant;                             //" start="66" end="67" valeur="" presence="F" type="N"/>

private String Date_de_lacte_de_la_délivrance_du_produit;            //" start="68" end="73" valeur="" presence="O" type="N"/>

private String Code_acte;                                            //" start="74" end="78" valeur="" presence="O" type="A"/>

private String Quantités_dactes;                                     //" start="79" end="80" valeur="" presence="O" type="N"/>

private String Coefficient;                                          //" start="81" end="86" valeur="" presence="O" type="N"/>

private String Dénombrement;                                         //" start="87" end="88" valeur="" presence="O" type="N"/>

private String Prix_unitaire;                                        //" start="89" end="95" valeur="" presence="O" type="N"/>

private String Base_de_remboursement;                                //" start="96" end="102" valeur="" presence="O" type="N"/>

private String Taux_applicable_à_la_prestation;                      //" start="103" end="105" valeur="" presence="O" type="N"/>

private String Montant_remboursable_par_lorganisme_dAssurance_Maladie;    //" start="106" end="112" valeur="" presence="O" type="N"/>

private String Montant_des_honoraires_ou_de_la_dépense;               //" start="113" end="119" valeur="" presence="O" type="N"/>

private String Qualificatif_de_la_dépense;                            //" start="120" end="120" valeur="" presence="O" type="A"/>

private Enum   Code_lieu;//o ou 1                                       //" start="121" end="121" valeur="" presence="O" type="N">

private String Montant_remboursable_par_lorganisme_complémentaire;    //" start="122" end="128" valeur="" presence="F" type="N"/>

}