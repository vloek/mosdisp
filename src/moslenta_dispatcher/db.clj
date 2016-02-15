(ns moslenta-dispatcher.db
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:gen-class))

(def conn (mg/connect))

(def db (mg/get-db conn "moslenta-dispatcher"))

(defn insert-data [data]
  "Insert entity to topics tree"
  (mc/insert-and-return db "topics" data))

(defn find-entity
  "Find one topic by id"
  [id]
  (mc/find-one db "topics" {:id id}))

(defn find-user
  "Find user by id"
  [id]
  (mc/find-one db "users" {:id id}))

(defn find-topics-by-user-id
  "Finder"
  [user_id]
  (mc/find))