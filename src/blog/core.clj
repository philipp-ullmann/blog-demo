(ns blog.core
  (require [org.httpkit.server :as http]
           [taoensso.timbre    :as log]

           [ring.middleware [params :refer [wrap-params]]
                            [cors   :refer [wrap-cors]]
                            [logger :refer [wrap-with-logger]]]

           [compojure [core  :refer [defroutes]]
                      [route :as route]]

           [blog [comments :as comments]
                 [db       :as db]])

  (import [org.apache.commons.daemon Daemon DaemonContext])
  (:gen-class :implements [org.apache.commons.daemon.Daemon]))

;; Ring setup
;; ==========================================================================================================

(defroutes routes
  comments/routes
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> routes
      (wrap-cors :access-control-allow-origin  #".*"
                 :access-control-allow-methods [:get :put :post :delete]
                 :access-control-allow-headers ["Origin" "X-Requested-With" "Content-Type" "Accept"])
      wrap-params
      (wrap-with-logger :info  (fn [x] (log/info x))
                        :debug (fn [x])
                        :error (fn [x] (log/error x))
                        :warn  (fn [x] (log/warn x)))))

;; Server
;; ==========================================================================================================

(defonce server (atom nil))

(defn- start-server []
  (let [port 8000]
    (reset! server (http/run-server app {:port port :join? false}))
    (log/info "Server started on port:" port)))

(defn- init-db []
  (db/create-comments-table! db/con)
  (db/insert-comment<!       db/con "Comment 1" "This is a comment" "http://www.google.at"))

;; Daemon implementation
;; ==========================================================================================================

(defn -init [this ^DaemonContext context]
  (db/drop-comments-table! db/con)
  (init-db))

(defn -destroy [this])

(defn -start [this]
  (start-server))

(defn -stop [this]
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)
    (log/info "Server stopped")))

;; Main method
;; ================================================================================================

(defn -main [& args]
  (db/drop-comments-table! db/con)
  (init-db)
  (start-server))
