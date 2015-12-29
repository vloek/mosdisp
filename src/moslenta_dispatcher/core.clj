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
    (load-from-url url)))

(defn -main [& args]
  (load-content))
