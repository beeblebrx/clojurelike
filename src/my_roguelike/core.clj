(ns my_roguelike.core
  (:require [lanterna.screen :as s])
  (:gen-class :main true))

(load "roomgens")
(load "levelgens")

(defn get-glyph
  [tile-type]
  (get {:floor ".", :wall "#"} tile-type))

(defn generate-world
  [levels]
  (loop [map-of-levels {}
         n 1]
    (if (> n levels)
      map-of-levels
      (recur (assoc map-of-levels (keyword (str n)) (level-generator)) (inc n)))))

(defn start-game
  []
  {:turn 1 :world (generate-world 1)})

(defn next-turn
  [game-state]
  {:turn (inc (:turn game-state))})

(defn draw-game
  [game screen]
  (s/clear screen)
  (doseq [x (range 80)
          y (range 25)]
    (let [tile (get-in game [:world :1 :tilebuffer [x y]])]
      (when (not (nil? tile))
        (s/put-string screen x y (get-glyph (:type tile))))))
  (s/redraw screen)
  (s/get-key-blocking screen))

(defn run-game
  [game screen]
  (draw-game game screen))

(defn main
  ([] (main :swing false))
  ([screen-type] (main screen-type false))
  ([screen-type block?]
     (letfn [(go []
                 (let [screen (s/get-screen screen-type)]
                   (s/in-screen screen
                                (run-game (start-game) screen))))]
       (if block?
         (go)
         (future (go))))))

(defn -main [& args]
  (let [args (set args)
        screen-type (cond
                     (args ":swing") :swing
                     (args ":text")  :text
                     :else           :auto)]
    (main screen-type true)))
