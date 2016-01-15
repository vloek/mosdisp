(ns moslenta-dispatcher.judgr.mongo.db)
(in-ns 'judgr.mongo.db)

(deftype MongoDB [settings conn]
  ConnectionBasedDB
  (get-connection [db]
    conn)

  FeatureDB
  (add-item! [db item class]
    (ensure-valid-class settings class
                        (let [data {:item  item
                                    :class class}]
                          (mongodb/with-mongo conn
                                              (mongodb/insert! :items data))
                          data)))

  (add-feature! [db item feature class]
    (ensure-valid-class settings class
                        (mongodb/with-mongo conn
                                            (mongodb/update! :features {:feature feature}
                                                             {:$inc {:total                                 1
                                                                     (str (name :classes) "." (name class)) 1}})
                                            (.get-feature db feature))))

  (clean-db! [db]
    (mongodb/with-mongo conn
                        (mongodb/destroy! :items {})
                        (mongodb/destroy! :features {})))

  (get-feature [db feature]
    (mongodb/with-mongo conn
                        (let [feature (mongodb/fetch-one :features
                                                         :where {:feature feature})]
                          (dissoc feature :_id))))

  (count-features [db]
    (mongodb/with-mongo conn
                        (mongodb/fetch-count :features)))

  (get-items [db]
    (mongodb/with-mongo conn
                        (map #(hash-map :item (:item %) :class (keyword (:class %)))
                             (mongodb/fetch :items))))

  (count-items [db]
    (mongodb/with-mongo conn
                        (mongodb/fetch-count :items)))

  (count-items-of-class [db class]
    (mongodb/with-mongo conn
                        (mongodb/fetch-count :items
                                             :where {:class class}))))