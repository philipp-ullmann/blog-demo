(defproject blog "0.1.0-SNAPSHOT"
  :description  "Blog demo server to recruit frontend programmers"
  :url          "https://bitbucket.org/create-at/blog-demo"
  :license      {:name "Eclipse Public License"
                 :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [
                 ;; Business

                 [org.clojure/clojure               "1.6.0"]
                 [environ                           "1.0.0"]
                 [org.apache.commons/commons-daemon "1.0.9"]
                 [com.taoensso/timbre               "3.3.1"]
                 [faker                             "0.2.2"]
                 [clojure-humanize                  "0.1.0"]

                 ;; Database

                 [clojure.jdbc                      "0.3.0"]
                 [clojure.jdbc/clojure.jdbc-c3p0    "0.3.0"]
                 [com.h2database/h2                 "1.4.182"]
                 [yesql                             "0.4.0"]

                 ;; Web

                 [http-kit                          "2.1.19"]
                 [liberator                         "0.12.2"]
                 [ring-cors                         "0.1.4"]
                 [ring.middleware.logger            "0.5.0"]
                 [compojure                         "1.2.0"]]

  :profiles      {:dev {:dependencies [[ring-mock "0.1.5"]
                                       [midje     "1.6.3"]
                                       [cheshire  "5.3.1"]]}}

  :main         blog.core
  :aot          :all
  :jar-name     "blog.jar"
  :uberjar-name "blog-standalone.jar")
