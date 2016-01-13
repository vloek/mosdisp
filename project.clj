(defproject moslenta_dispatcher "0.1.0-SNAPSHOT"
  :description "dispatcher and analyse text"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.novemberain/monger "3.0.0-rc2"]
                 [org.clojure/core.async "0.2.374"]
                 [judgr "0.3.0"]
                 [judgr/mongodb "0.1.3"]]
  :main moslenta-dispatcher.core)
