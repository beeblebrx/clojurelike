(in-ns 'my_roguelike.core)

(defrecord Tile [type lit])

(defn sqr-room-tile-type
  [n size]
  (if (or (< n size)
          (> n (- (* size size) size))
          (= 0 (mod n size))
          (= (dec size) (rem n size)))
    :wall
    :floor))

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
                                   (Tile. (sqr-room-tile-type n size) true)))))))))
