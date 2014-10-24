(ns blog.comments-test
  (require [midje.sweet       :refer :all]
           [ring.mock.request :refer :all]
           [cheshire.core     :refer [parse-string]]
           [blog.core         :refer [app]]
           [blog.db           :as db]))

(with-state-changes [(before :facts (do (db/drop-comments-table!   db/con)
                                        (db/create-comments-table! db/con)
                                        (db/seed! 2)))]

;; List comments
;; ====================================================================================================================

(fact "List all available comments"
  (let [resp (app (request :get "/comments"))]

    (-> resp :headers (get "Content-Type")) => "application/json;charset=UTF-8"
    (-> resp :status)                       => 200
    (-> resp :body parse-string)            => (just [(just {"id" 2 "title" string? "content" string?})
                                                      (just {"id" 1 "title" string? "content" string?})])))

;; Show comment details
;; ====================================================================================================================

(fact "Show details of a comment"
  (let [resp (app (request :get "/comments/1"))]

    (-> resp :headers (get "Content-Type")) => "application/json;charset=UTF-8"
    (-> resp :status)                       => 200
    (-> resp :body parse-string)            => (just {"id" 1 "title" string? "content" string? "video" "/smile.mp4"})))

(fact "Show details of a comment that does not exist"
  (let [resp (app (request :get "/comments/3"))]

    (-> resp :headers (get "Content-Type")) => "text/plain"
    (-> resp :status)                       => 404
    (-> resp :body)                         => "Resource not found.")))
