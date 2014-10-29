(ns blog.comments
  (require [compojure.core           :refer [defroutes GET]]
           [liberator.core           :refer [defresource]]
           [clojure.contrib.humanize :refer [truncate]]
           [blog.db                  :as db]))

;; GET /comments
;; List all comments order by id descending.
;; ================================================================================================

(defresource index
  :allowed-methods       [:get]

  :available-media-types ["application/json"]

  :handle-ok             (fn [_]
                           (->> (db/all-comments db/con)
                                (map (comp #(select-keys % [:id :title :content])
                                           #(assoc % :content (truncate (:content %) 60)))))))

;; GET => /comments/:id
;; Show details of a comment.
;; ================================================================================================

(defresource show
  :allowed-methods       [:get]

  :available-media-types ["application/json"]

  :exists?               (fn [{{params :params} :request}]
                           (let [c (first (db/comment-by-id db/con (:id params)))]
                             [c {::c c}]))

  :handle-ok             (fn [{c ::c}] c))

;; Routes
;; ================================================================================================

(defroutes routes
  (GET "/comments"     request index)
  (GET "/comments/:id" request show))
