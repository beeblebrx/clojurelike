(defproject my_roguelike "0.1.0-SNAPSHOT"
  :description "This is my roguelike"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojure-lanterna "0.9.4"]]
  :profiles {:dev {:dependencies [[speclj "3.1.0"]]}}
  :plugins [[speclj "3.1.0"]
            [cider/cider-nrepl "0.7.0"]]
  :test-paths ["spec"]
  :main my_roguelike.core)
