(ns blog.db
  (require [yesql.core :refer [defqueries]]))

(def con {:classname   "org.h2.Driver"
          :subprotocol "h2"
          :subname     "mem:blog_demo;DB_CLOSE_DELAY=-1"})

(defqueries "blog/db.sql")
