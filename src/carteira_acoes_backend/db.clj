(ns carteira-acoes-backend.db)

(def transacoes
  (atom [])) ; lista de maps

(defn registrar! [t]
      (swap! transacoes conj t))

(defn filtrar-por-periodo [inicio fim]
      (filter
        (fn [{:keys [data]}]
            (and (>= data inicio) (<= data fim)))
        @transacoes))

(defn saldo []
      (reduce
        (fn [acc {:keys [ticker tipo quantidade preco]}]
            (let [valor (* quantidade preco)]
                 (if (= tipo "COMPRA")
                   (+ acc (- valor))
                   (+ acc valor))))
        0
        @transacoes))
