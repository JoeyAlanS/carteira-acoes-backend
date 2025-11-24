(ns carteira-acoes-backend.routes
    (:require [compojure.core :refer :all]
      [carteira-acoes-backend.brapi :as brapi]
      [carteira-acoes-backend.db :as db]))

(defroutes api-routes

           (GET "/" []
                {:status 200 :body {:mensagem "API carteira de ações ativa"}})

           (GET "/acao/:ticker" [ticker]
                {:status 200 :body (brapi/buscar-acao ticker)})

           (POST "/compra" {body :body}
                 (let [{:keys [ticker quantidade data]} body
                       preco (:preco (brapi/buscar-acao ticker))]
                      (db/registrar! {:ticker ticker
                                      :tipo "COMPRA"
                                      :quantidade quantidade
                                      :preco preco
                                      :data data})
                      {:status 200 :body {:status "OK"}}))

           (POST "/venda" {body :body}
                 (let [{:keys [ticker quantidade data]} body
                       preco (:preco (brapi/buscar-acao ticker))]
                      (db/registrar! {:ticker ticker
                                      :tipo "VENDA"
                                      :quantidade quantidade
                                      :preco preco
                                      :data data})
                      {:status 200 :body {:status "OK"}}))

           (GET "/extrato" [inicio fim]
                {:status 200 :body (db/filtrar-por-periodo (Integer/parseInt inicio)
                                                           (Integer/parseInt fim))})

           (GET "/saldo" []
                {:status 200 :body {:saldo (db/saldo)}}))
