(ns moslenta-dispatcher.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:gen-class))

(defdb prod
       (postgres { :db "articles"
                   :user "db"
                   :password "dbpass"}))

(defentity articles)

(def last-articles [count]
  (-> select *))