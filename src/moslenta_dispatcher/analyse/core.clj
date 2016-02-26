(ns moslenta-dispatcher.analyse.core
  (:require [judgr.core]
            [clojure.string :as str]
            [taoensso.carmine :as car :refer (wcar)]
            [moslenta-dispatcher.db :as db])
  (:use [judgr.core]
        [judgr.settings]))

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
  (classifier)))

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

(defn add-weight
  "Additional weight to article by classifier"
  [classifier article]
  (merge article (.probabilities classifier
                                 (:body article))))

(defn articles-with-weight
  "Take articles with weight by classifier"
  ([classifier articles]
   (articles-with-weight classifier articles 20))

  ([classifier articles count]
   (take count (map #(add-weight classifier %) articles))))

(defn articles-for-user
  ([user-id]
  (articles-for-user user-id 20))

  ([user-id count]
  (articles-with-weight (classifier-for-user user-id)
                        (db/last-articles count))))