(in-ns 'my_roguelike.core)

(defn square-arena
  [x y size]
  (square-room-gen x y size))

(defn level-generator
  ([] (level-generator (square-arena 0 0 3)))
  ([generator]
     {:tilebuffer (generator)}))
