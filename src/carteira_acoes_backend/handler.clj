(ns carteira-acoes-backend.handler
    (:require [carteira-acoes-backend.routes :refer [api-routes]]
      [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
      [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))

(def app
  (-> api-routes
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))
