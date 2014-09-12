
ALTER TABLE "nav"."medicinecatlog"
ADD COLUMN "diseaseid" int4,
ADD CONSTRAINT "diseaseid" FOREIGN KEY (diseaseid) REFERENCES "nav"."diseasemaster" ("id") ON DELETE RESTRICT ON UPDATE NO ACTION;

INSERT INTO diseasemaster(name) VALUES  ('Prostate Cancer');
INSERT INTO diseasemaster(name) VALUES  ('Lung Cancer');
INSERT INTO diseasemaster(name) VALUES  ('Colon Cancer');
INSERT INTO diseasemaster(name) VALUES  ('Rectal Cancer');

INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Methotrexate','Abitrexate','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ado-Trastuzumab emtansine','Kadcyla','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Anastrozole','Arimidex','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Capecitabine','Xeloda','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Carboplatin','Paraplatin','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cisplatin','Platinol and Platinol-AQ','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cyclophosphamide','Cytoxan, Clafen, Neosar','IV, oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Docetaxel','Taxotere','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Doxorubicin HCl','Adriamycin PFS','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Epirubicin Hydrochloride','Ellence','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Etoposide','VePesid, Etopophos, Toposar','IV, oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Everolimus','Afinitor','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Exemestane','Aromasin','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fareston','Toremifene','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fluoxymesterone','Halotestin','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fulvestrant','uo','IM','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Gemcitabine Hydrochloride','Gemzar','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Letrozole','Femara','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fluorouracil','Adrucil, Fluoroplex','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Goserelin acetate','Zoladex','Subcutaneous','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ixabepilone','Ixempra','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Lapatinib','Tykerb','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Leucovorin','Wellcovorin, Citrovorum Factor, Folinic Acid','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Megestrol Acetate','Megace','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Methotrexate','Abitrexate, Folex, Mexate, Mexate AQ','oral, IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Mitomycin','Mitosol','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Mitoxantrone','Novantrone','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Paclitaxel (Albumin)','Abraxane','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Paclitaxel','Taxol','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Pamidronate Disodium','Aredia','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Pertuzumab','Perjeta','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Tamoxifen','Nolvadex','oral','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Thiotepa','Thioplex','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Trastuzumab','Herceptin','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinblastine','Velban','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinorelbine','Navelbine','IV','1');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Abiraterone Acetate','Zytiga','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Bicalutamide','Casodex','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cabazitaxel','Jevtana','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cyclophosphamide','Cytoxan','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Degarelix','Firmagon','Subcutaneous inj','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Denosumab','Prolia, Xgeva','Subcutaneous inj','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Docetaxel','Taxotere','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Enzalutamide','Xtandi','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Estramustine Phosphate','Emzyt','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Flutamide','Eulexin','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Goserelin','Zoladex','Subcutaneous inj','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ketoconazole','Xolegel, Extina, Nizoral','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Leuprolide Acetate','Lupron, Depot, 3 month, 4 month, Ped, Viadur','IM/Subcutaneous inj','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Mitoxantrone','Novantrone, DHAD, DHAQ','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Prednisone','Deltasone','oral','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Radium 223 Dichloride','Xofigo','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Sipuleucel-T','Provenge','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinblastine','Velban','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinorelbine','Navelbine','IV','2');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Afatinib Dimaleate','Gilotrif','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Bevacizumab','Avastin','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Carboplatin','Paraplatin','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ceritinib','Zykadia','oral','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cisplatin','Platinol, Platinol AQ','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Crizotinib','Xalkori','oral','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Docetaxel','Taxotere','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Erlotinib','Tarceva','oral','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Etoposide','VePesid, Etopophos, Toposar, etoposide phosphate, VP-16','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Gefitinib','Iressa','oral','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Gemcitabine','Gemzar','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('irinotecan','Camptosar','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Methotrexate','Abitrexate, Folex PFS, Folex, Methotrexate LPF, Mexate-AQ','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Paclitaxel Albumin-stabilized Nanoparticle Formulation','Abraxane','','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Paclitaxel','Taxol','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Pemetrexed Disodium','Alimta','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinblastine','Velban, Velbe, Velsar','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Vinorebine','Navelbine','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Etoposide','Vepesid, Toposar, Etopophos,','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Methotrexate','Abitrexate, Folex PFS, Mexate-AQ','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Topotecan','Hycamtin','IV','3');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Bevacizumab','Avastin','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Capecitabine','Xeloda','oral','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cetuximab','Erbitux','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fluoruracil','Adrucil, Efudex','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Irinotecan','Camptosar','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Leucovorin','Wellcovorin','','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Oxaliplatin','Eloxatin','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Panitumumab','Vectibix','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Regorafenib','Stivarga','oral','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ziv-Aflibercept','Zaltrap','IV','4');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Bevacizumab','Avastin','IV','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Cetuximab','Erbitux','IV','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Fluoruracil','Adrucil, Efudex','IV','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Irinotecan','Camptosar','IV','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Panitumumab','Vectibix','IV','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Regorafenib','Stivarga','oral','5');
INSERT INTO medicinecatlog   (genericname,  brandname ,method ,diseaseid) VALUES  ('Ziv-Aflibercept','Zaltrap','IV','5');