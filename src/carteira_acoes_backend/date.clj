(ns carteira-acoes-backend.date
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(def fmt-ddMMyyyy (DateTimeFormatter/ofPattern "ddMMyyyy"))
(def fmt-dd-MM-yyyy (DateTimeFormatter/ofPattern "dd/MM/yyyy"))

(defn parse-ddmmyyyy
  "Converte string ddmmyyyy para LocalDate"
  [s]
  (LocalDate/parse s fmt-ddMMyyyy))

(defn format-localdate
  "Converte LocalDate para string dd/MM/yyyy"
  [ld]
  (.format ld fmt-dd-MM-yyyy))
