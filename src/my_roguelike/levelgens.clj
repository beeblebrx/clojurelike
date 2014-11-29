(in-ns 'my_roguelike.core)

(defn square-arena
  [x y size]
  (fn [] (square-room-gen x y size)))

(defn round-arena
  [center-x center-y radius]
  (fn [] (round-room-gen center-x center-y radius)))

(defn level-generator
  ([] (level-generator (round-arena 5 5 4)))
  ([generator]
     {:tilebuffer (build-walls (generator))}))
