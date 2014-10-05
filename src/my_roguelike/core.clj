(ns my_roguelike.core)

(load "roomgens")
(load "levelgens")

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
