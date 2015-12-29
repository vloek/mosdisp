(ns moslenta-dispatcher.core
  (:require [clojure.data.json :as json]
            [moslenta-dispatcher.db :as db]))

(defn load-from-url 
  "Content from url to object"
  [url]
  (-> (slurp url)
      str
      json/read-str))

(defn select-entity
  "Select keys from entity"
  [data]
  (select-keys data ["url"
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

(def select-entity-and-save
  (comp select-entity db/insert-data))

(defn dispatch
  "Dispatch entity to db from api"
  []
  (map (select-entity-and-save)
       (load-content)))

(defn -main [& args]
  (dispatch))
