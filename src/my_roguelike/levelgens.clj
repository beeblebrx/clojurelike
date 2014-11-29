(in-ns 'my_roguelike.core)

(defn square-arena
  [x y size]
  (square-room-gen x y size))

(defn level-generator
  ([] (level-generator (round-room-gen 5 5 4)))
  ([generator]
     {:tilebuffer (build-walls (generator))}))
