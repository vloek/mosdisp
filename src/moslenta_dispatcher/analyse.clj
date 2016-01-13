(ns moslenta-dispatcher.analyse
  (:require [judgr.core]
            [clojure.core.string :as str])
  (:use [judgr.core]
        [judgr.settings]
        [judgr.mongo.db]))

(def new-settings
  (update-settings settings
                   [:database :type] :mongo
                   [:database :mongo] {:database "classifficator"
                                       :host     "localhost"
                                       :port     27017
                                       :auth?    false
                                       :username ""
                                       :password ""}
                   [:classifier :default] {:smoothing-factor 4}))

(def classifier (classifier-from new-settings))

(defn remove-trash [text]
  (join " "
        (remove #(< (count %1) 4)
                (str/split text #" "))))

(defn like [text user_id]
  (custom-train user_id text :liked))

(defn dislike [text user_id]
  (custom-train user_id text :disliked))

(defn custom-train [user_id text tag]
  (.train! (classifier-by-user user_id)
           (remove-trash text)
           tag))

(defn classifier-by-user [user_id]
  classifier)