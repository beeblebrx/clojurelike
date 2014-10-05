(in-ns 'my_roguelike.core)

(defn square-arena
  [size]
  (square-room-gen 0 0 size))

(defn level-generator
  ([] (level-generator (square-arena 3)))
  ([generator]
     {:tilebuffer (generator)}))
