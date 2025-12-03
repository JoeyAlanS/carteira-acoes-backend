(ns carteira-acoes-backend.db
  (:require [carteira-acoes-backend.date :as dt]))

(def transacoes
  (atom []))

(defn registrar! [t]
  (swap! transacoes conj t))

(defn filtrar-por-periodo [inicio fim]
  (filter
   (fn [{:keys [data]}]
     (and (not (.isBefore data inicio))
          (not (.isAfter data fim))))
   @transacoes))

(defn saldo []
  (reduce
   (fn [acc {:keys [tipo quantidade preco]}]
     (let [valor (* quantidade preco)]
       (if (= tipo "COMPRA")
         (- acc valor)
         (+ acc valor))))
   0
   @transacoes))

(defn carteira []
  (let [agrupar (group-by :ticker @transacoes)]
    (reduce
     (fn [acc [ticker ts]]
       (let [qtd (reduce
                  (fn [soma {:keys [tipo quantidade]}]
                    (if (= tipo "COMPRA")
                      (+ soma quantidade)
                      (- soma quantidade)))
                  0
                  ts)
             nome (:nome (first ts))]
         (if (pos? qtd)
           (conj acc {:ticker ticker
                      :nome nome
                      :quantidade qtd})
           acc)))
     []
     agrupar)))

(defn quantidade-por-ticker [ticker]
  (reduce
   (fn [acc {:keys [tipo quantidade]}]
     (if (= tipo "COMPRA")
       (+ acc quantidade)
       (- acc quantidade)))
   0
   (filter #(= (:ticker %) ticker) @transacoes)))
