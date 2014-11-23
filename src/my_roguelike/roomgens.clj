(in-ns 'my_roguelike.core)

(defrecord Tile [type lit])

(defn surrounding-coords
  [x y]
  (for [xx [(dec x) x (inc x)]
        yy [(dec y) y (inc y)]]
    [xx yy]))

(defn get-wall-coords
  [tilebuffer]
  (reduce into #{}
          (map #(for [tile-xy (surrounding-coords (first %) (second %))
                      :when (nil? (tilebuffer tile-xy))]
                  tile-xy)
               (keys tilebuffer))))

(defn build-walls
  [tilebuffer]
  (loop [tiles tilebuffer
         walls (get-wall-coords tilebuffer)]
    (if (empty? walls)
      tiles
      (recur (assoc tiles (first walls) (Tile. :wall false))
             (rest walls)))))

(defn square-room-gen
  ([start-x start-y size]
     (fn []
       (when (> size 0)
         (loop [n 0
                tiles {}]
           (if (= n (* size size))
             tiles
             (recur (inc n) (assoc tiles [(+ start-x (mod n size))
                                          (+ start-y (quot n size))]
                                   (Tile. :floor true)))))))))
