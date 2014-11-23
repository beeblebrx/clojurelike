(in-ns 'my_roguelike.core)

(defn square-arena
  [x y size]
  (square-room-gen x y size))

(defn level-generator
  ([] (level-generator (square-arena 1 1 3)))
  ([generator]
     {:tilebuffer (build-walls (generator))}))
