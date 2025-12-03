(ns carteira-acoes-backend.routes
  (:require [compojure.core :refer :all]
            [carteira-acoes-backend.brapi :as brapi]
            [carteira-acoes-backend.db :as db]
            [carteira-acoes-backend.date :as dt]))

(defn json-transacao
  "Converte LocalDate → dd/MM/yyyy para enviar no JSON"
  [t]
  (-> t
      (update :data dt/format-localdate)))

(defroutes api-routes

  ;; API ok
  (GET "/" []
       {:status 200 :body {:mensagem "API carteira de acoes ativa"}})

  ;; Consulta ações
  (GET "/acao/:ticker" [ticker]
       {:status 200 :body (brapi/buscar-acao ticker)})

  ;; Compra
  (POST "/compra" {body :body}
        (let [{:keys [ticker quantidade data]} body
              data-local (dt/parse-ddmmyyyy data)
              preco (:preco (brapi/buscar-acao ticker))
              trans {:ticker ticker
                     :tipo "COMPRA"
                     :quantidade quantidade
                     :preco preco
                     :data data-local}]
          (db/registrar! trans)
          {:status 200 :body {:status "OK"
                              :transacao (json-transacao trans)}}))

  ;; Venda
  (POST "/venda" {body :body}
        (let [{:keys [ticker quantidade data]} body
              data-local (dt/parse-ddmmyyyy data)
              qtd-atual (db/quantidade-por-ticker ticker)]
          (cond
            (<= quantidade 0)
            {:status 400 :body {:erro "Quantidade invalida."}}

            (> quantidade qtd-atual)
            {:status 400 :body {:erro "Voce nao possui acoes suficientes para vender."}}

            :else
            (let [preco (:preco (brapi/buscar-acao ticker))
                  trans {:ticker ticker
                         :tipo "VENDA"
                         :quantidade quantidade
                         :preco preco
                         :data data-local}]
              (db/registrar! trans)
              {:status 200 :body {:mensagem "Venda registrada."
                                  :transacao (json-transacao trans)}}))))

  ;; Extrato por período
  (GET "/extrato" [inicio fim]
       (let [i (dt/parse-ddmmyyyy inicio)
             f (dt/parse-ddmmyyyy fim)]
         {:status 200
          :body (map json-transacao
                     (db/filtrar-por-periodo i f))}))

  ;; Saldo + carteira
  (GET "/saldo" []
       (let [s (db/saldo)
             c (db/carteira)]
         (if (empty? c)
           {:status 400 :body {:erro "Voce nao possui acoes na carteira."}}
           {:status 200
            :body {:saldo s
                   :carteira c}}))))
