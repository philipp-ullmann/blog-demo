(ns blog.db
  (require [jdbc.pool.c3p0 :as pool]
           [yesql.core     :refer [defqueries]]
           [faker.lorem    :as lorem]))

(def con (pool/make-datasource-spec {:classname         "org.h2.Driver"
                                     :subprotocol       "h2"
                                     :subname           "mem:blog_demo"
                                     :initial-pool-size 3
                                     :min-pool-size     3
                                     :max-pool-size     10}))

(defqueries "blog/db.sql")

(defn seed! [amount]
  "Seeds the database with data."
  (doseq [[title content] (map list (take amount (lorem/words))
                                    (take amount (lorem/paragraphs)))]

    (insert-comment<! con title content "http://www.google.at")))
