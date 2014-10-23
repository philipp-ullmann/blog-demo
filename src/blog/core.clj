(ns blog.core
  (require [blog.db :as db])
  (:gen-class))

(defn -main [& args]
  (db/create-comments-table! db/con)
  (db/insert-comment<!       db/con "Comment 1" "This is a comment" "http://www.google.at")

  (println (db/all-comments db/con)))
