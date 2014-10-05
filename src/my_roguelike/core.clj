(ns my_roguelike.core)

(defrecord Tile [type lit])

(defn square-room-gen
  ([start-x start-y size]
     (fn [] (into {}
                  (for [x (range start-x (+ start-x size))
                        y (range start-y (+ start-y size))
                        :let [tile-map {[x y] (Tile. :basic true)}]]
                    tile-map)))))

(defn square-arena
  [size]
  (square-room-gen 0 0 size))

(defn level-generator
  ([] (level-generator (square-arena 3)))
  ([generator]
     {:tilebuffer (generator)}))

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
