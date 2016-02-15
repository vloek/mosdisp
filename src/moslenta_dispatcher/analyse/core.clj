(ns moslenta-dispatcher.analyse.core
  (:require [judgr.core]
            [clojure.string :as str])
  (:use [judgr.core]
        [judgr.settings]
        [judgr.redis.db]))

(def new-settings
  (update-settings settings
                   [:classes] [:liked :disliked]))
;                   [:database :type] :redis
;                   [:database :redis] {:database 0
;                                       :host     "localhost"
;                                       :port     6379
;                                       :password nil}
;                   [:classifier :default] {:smoothing-factor 4}
;                   [:classifier]
;                   [:classifier :default :tresholds] {:liked 1.2 :disliked 2.5}))


(defn items-for-user [user_id]

  )


(defn classifier-by-user [user_id]
  (let [classifier (classifier-from settings)]
    (.train-all! classifier (items-for-user user_id)))
  (classifier-from settings))

(defn remove-trash [text]
  (str/join " "
        (remove #(< (count %1) 4)
                (str/split text #" "))))

(defn custom-train [user_id text tag]
  (.train! (classifier-by-user user_id)
           (remove-trash text)
           tag))