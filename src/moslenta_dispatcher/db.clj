(ns moslenta-dispatcher.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(def conn (mg/connect))

(def db (mg/get-db conn "moslenta-dispatcher"))

(defn insert-data [data]
  "Insert entity to topics tree"
  (mc/insert-and-return db "topics" data))

(defn find-entity [id]
  (mc/find-one db "topics" {:id id}))