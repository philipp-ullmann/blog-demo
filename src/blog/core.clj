(ns blog.core
  (require [org.httpkit.server :as http]
           [taoensso.timbre    :as log]
           [environ.core       :refer [env]]

           [ring.middleware [cors   :refer [wrap-cors]]
                            [logger :refer [wrap-with-logger]]]

           [compojure [core  :refer [defroutes]]
                      [route :as route]]

           [blog [comments :as comments]
                 [db       :as db]]))

;; Ring setup
;; =====================================================================================================

(defroutes routes
  comments/routes
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> routes
      (wrap-cors :access-control-allow-origin  #".*"
                 :access-control-allow-methods [:get :put :post :delete]
                 :access-control-allow-headers ["Origin" "X-Requested-With" "Content-Type" "Accept"])
      (wrap-with-logger :info  (fn [x] (log/info x))
                        :debug (fn [x])
                        :error (fn [x] (log/error x))
                        :warn  (fn [x] (log/warn x)))))

;; Main method
;; =====================================================================================================

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (db/drop-comments-table!   db/con)
    (db/create-comments-table! db/con)
    (db/seed! 50)
    (http/run-server app {:port port :join? false})
    (log/info "Server started on port:" port)))
