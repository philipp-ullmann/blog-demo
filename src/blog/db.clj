(ns blog.db
  (require [yesql.core  :refer [defqueries]]
           [faker.lorem :as lorem]))

(def con {:classname         "org.h2.Driver"
          :subprotocol       "h2"
          :subname           "mem:blog_demo;DB_CLOSE_DELAY=-1"
          :make-pool?        true
          :minimum-pool-size 5
          :maximum-pool-size 20})

(defqueries "blog/db.sql")

(defn seed! [amount]
  "Seeds the database with data."
  (doseq [[title content] (map list (take amount (lorem/words))
                                    (take amount (lorem/paragraphs)))]

    (insert-comment<! con title content "http://www.google.at")))
