(ns carteira-acoes-backend.brapi
  (:require [clj-http.client :as http]
            [environ.core :refer [env]]))

(def token (env :brapi-token))

(defn buscar-acao [ticker]
  (let [url (str "https://brapi.dev/api/quote/" ticker "?token=" token)
        resp (http/get url {:as :json})
        d (-> resp :body :results first)]
    {:ticker ticker
     :nome (:longName d)
     :preco (:regularMarketPrice d)
     :maxima_do_dia (:regularMarketDayHigh d)
     :minima_do_dia (:regularMarketDayLow d)
     :abertura (:regularMarketOpen d)
     :fechamento (:regularMarketPreviousClose d)
     :hora (:regularMarketTime d)}))
