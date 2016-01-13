(ns moslenta-dispatcher.core
  (:require [clojure.data.json :as json]
            [moslenta-dispatcher.db :as db]
            [clojure.core.async :as a :refer [>! <! <!! >!! go chan buffer close! go-loop
                                              thread alts! alts!! timeout sliding-buffer]]))

(defn load-from-url 
  "Content from url to object"
  [url]
  (-> (slurp url)
      str
      json/read-str))

(defn select-entity
  "Select keys from entity"
  [data]
  (select-keys data ["id"
                     "url"
                     "title"
                     "body"
                     "rubric_title"
                     "subrubric"
                     "published_date"]))

(defn load-content
  "Loaded content from moslenta.ru."
  []
  (let [url "http://moslenta.ru/export/search.json?from=1451290000"]
    (try (load-from-url url)
         (catch Exception e nil))))

(defn blank?
  [x]
  (nil? (db/find-entity (x "id"))))

(defn dispatch
  "Dispatch entity to db from api"
  []
  (map (comp db/insert-data select-entity)
       (filter blank?
               (load-content))))

(defn -main [& args]
  (println "running..")
  (println (dispatch)))
