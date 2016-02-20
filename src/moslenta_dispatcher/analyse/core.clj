(ns moslenta-dispatcher.analyse.core
  (:require [judgr.core]
            [clojure.string :as str])
  (:use [judgr.core]
        [judgr.settings]))

(def new-settings
  (update-settings settings
                   [:classes] [:liked :disliked]
;                   [:database :type] :redis
;                   [:database :redis] {:database 0
;                                       :host     "localhost"
;                                       :port     6379
;                                       :password nil}
                   [:classifier :default] {:smoothing-factor 4}
                   [:classifier :default :tresholds] {:liked 1.2 :disliked 2.5}
                   [:classifier]))

(defn items-for-user [user_id]
  )

(defn classifier-by-user [user_id]
  (let [classifier (classifier-from new-settings)]
    (.train-all! classifier (items-for-user user_id))
  (classifier-from settings)))

(defn classifier-for-test []
  (let [classifier (classifier-from new-settings)]
      (.train-all! classifier ["пожар" "сгорели все" "гореть хорошо"] :liked)
      (.train-all! classifier ["морковь" "кролики" "зайчики" "мыши едят морковку"] :disliked)
      classifier))


(defn remove-trash [text]
  (str/join " "
        (remove #(< (count %1) 4)
                (str/split text #" "))))

(defn custom-train [user_id text tag]
  (.train! (classifier-by-user user_id)
           (remove-trash text)
           tag))


(defprotocol IClassified
  "Classified protocol"
  (weight-by-classifier [this classifier]))


(defrecord Article [title content]
  IClassified
  (weight-by-classifier [this classifier]
    (.classify classifier content)))


(weight-by-classifier (Article. "test" "test") (classifier-for-test))




