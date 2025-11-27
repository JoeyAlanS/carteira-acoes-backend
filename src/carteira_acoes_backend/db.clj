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
                    ts)]
          (if (pos? qtd)
            (conj acc {:ticker ticker :quantidade qtd})
            acc)))
      []
      agrupar)))
