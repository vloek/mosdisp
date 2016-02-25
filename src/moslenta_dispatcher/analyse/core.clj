(ns moslenta-dispatcher.analyse.core
  (:require [judgr.core]
            [clojure.string :as str]
            [taoensso.carmine :as car :refer (wcar)])
  (:use [judgr.core]
        [judgr.settings])
  (:import (moslenta_dispatcher.analyse.core PScheduler)))

(def new-settings
  (update-settings settings
                   [:classes] [:liked :disliked]
                   [:classifier :default] {:smoothing-factor 4}
                   [:classifier :default :tresholds] {:liked 1.2 :disliked 2.5}
                   [:classifier]))

(defn items-for-user [user_id])

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


(defn classifier-for-user
  "classifier by user id"
  [user-id])


(defn last-articles
  "Select last articles by count"
  [count])


(defn next-articles
  ([classifier articles]
   (next-articles classifier articles 20))
  ([classifier articles count]))

(classifier-for-user user-id)
  (train (select 100 user-ratings))

(next-20-articles (last-100-articles))

