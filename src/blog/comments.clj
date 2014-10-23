(ns blog.comments
  (require [compojure.core :refer [defroutes context GET]]
           [liberator.core :refer [defresource]]
           [blog.db        :as db]))

(defresource index
  :allowed-methods       [:get]

  :available-media-types ["application/json"]

  :handle-ok             (fn [_]
                           (->> (db/all-comments db/con)
                                (map #(select-keys % [:id :title :content])))))

(defresource show
  :allowed-methods       [:get]

  :available-media-types ["application/json"]

  :exists?               (fn [{{params :params} :request}]
                           (let [c (first (db/comment-by-id db/con (:id params)))]
                             [c {::c c}]))

  :handle-ok             (fn [{c ::c}] c))

(defroutes routes
  (GET "/comments"     request index)
  (GET "/comments/:id" request show))
