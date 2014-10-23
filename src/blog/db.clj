(ns blog.db
  (require [yesql.core  :refer [defqueries]]
           [faker.lorem :as lorem]))

(def con {:classname   "org.h2.Driver"
          :subprotocol "h2"
          :subname     "mem:blog_demo;DB_CLOSE_DELAY=-1"})

(defqueries "blog/db.sql")

(defn seed! []
  (doseq [title   (take 50 (lorem/words))
          content (take 50 (lorem/paragraphs))]

    (insert-comment<! con title content "http://www.google.at")))

(defn init! []
  (create-comments-table! con)
  (seed!))

(defn drop! []
  (drop-comments-table! con))
