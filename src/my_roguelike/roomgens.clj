(in-ns 'my_roguelike.core)

(defrecord Tile [type lit])

(defn square-room-gen
  ([start-x start-y size]
     (fn [] (into {}
                  (for [x (range start-x (+ start-x size))
                        y (range start-y (+ start-y size))
                        :let [tile-map {[x y] (Tile. :basic true)}]]
                    tile-map)))))
