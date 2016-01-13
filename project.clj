(defproject moslenta-dispatcher "0.1.0-SNAPSHOT"
  :description "dispatcher and analyse text"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [judgr/redis "0.2.2"]
                 [congomongo "0.4.7"]
                 [com.novemberain/monger "3.0.1"]
                 [org.clojure/core.async "0.2.374"]
                 [judgr "0.3.0"]
                 ]
  :main moslenta-dispatcher.core)
