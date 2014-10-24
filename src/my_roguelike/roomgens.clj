(in-ns 'my_roguelike.core)

(defrecord Tile [type lit])

(defn get-sqr-room-tile
  [x y start-x start-y size]
  (if (or (= x start-x)
          (= x (+ start-x (dec size)))
          (= y start-y)
          (= y (+ start-y (dec size))))
    :wall
    :floor))

(defn square-room-gen
  ([start-x start-y size]
     (fn [] (into {}
                  (for [x (range start-x (+ start-x size))
                        y (range start-y (+ start-y size))
                        :let [tile-map {[x y] (Tile.
                                               (get-sqr-room-tile x y start-x start-y size)
                                               true)}]]
                    tile-map)))))
