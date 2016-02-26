(ns moslenta-dispatcher.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:use [korma.db]
        [korma.core])
  (:gen-class))

(defdb prod
       (postgres { :db "articles"
                   :user "db"
                   :password "dbpass"}))

(defentity articles)

(defn last-articles
  "Select last articles by count"
  [count]
  (-> (select *)
      (where { :readed false})
      (order :created_at)
      (limit count)))