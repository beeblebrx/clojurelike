(ns my_roguelike.levels.roomgens
  (:refer-clojure))

(defrecord Tile [type lit])

(defn- surrounding-coords
  [x y]
  (for [xx [(dec x) x (inc x)]
        yy [(dec y) y (inc y)]]
    [xx yy]))

(defn- coords-inside
  [row tilebuffer]
  (let [row-keys (filter #(= row (second %)) (keys tilebuffer))
        xs (map first row-keys)]
    (map #(vector % row) (range (inc (apply min xs)) (apply max xs)))))

(defn- get-rows
  [tilebuffer]
  (into #{}
        (for [xy (keys tilebuffer)]
          (second xy))))

(defn- get-wall-coords
  [tilebuffer]
  (reduce into #{}
          (map #(for [tile-xy (surrounding-coords (first %) (second %))
                      :when (nil? (tilebuffer tile-xy))]
                  tile-xy)
               (keys tilebuffer))))

(defn- add-floor
  [x y tiles]
  (assoc tiles [x y] (Tile. :floor true)))

(defn- draw-circle
  [x y center-x center-y tiles]
  (->> tiles
       (add-floor (+ x center-x) (+ y center-y))
       (add-floor (+ y center-x) (+ x center-y))
       (add-floor (+ (- x) center-x) (+ y center-y))
       (add-floor (+ (- y) center-x) (+ x center-y))
       (add-floor (+ (- x) center-x) (+ (- y) center-y))
       (add-floor (+ (- y) center-x) (+ (- x) center-y))
       (add-floor (+ x center-x) (+ (- y) center-y))
       (add-floor (+ y center-x) (+ (- x) center-y))))

(defn- fill-room
  [tilebuffer]
  (loop [floor-coords (reduce into #{}
                              (map #(coords-inside % tilebuffer) (get-rows tilebuffer)))
         tiles tilebuffer]
    (if (empty? floor-coords)
      tiles
      (recur (rest floor-coords) (add-floor
                                  (first (first floor-coords))
                                  (second (first floor-coords))
                                  tiles)))))

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
     (when (> size 0)
       (loop [n 0
              tiles {}]
         (if (= n (* size size))
           tiles
           (recur (inc n) (assoc tiles [(+ start-x (mod n size))
                                        (+ start-y (quot n size))]
                                 (Tile. :floor true))))))))

(defn ring-room-gen
  [center-x center-y radius]
  (loop [x radius
         y 0
         radiusError (- 1 x)
         tiles {}]
    (if (>= x y)
      (if (neg? radiusError)
        (recur x (inc y) (+ radiusError (inc (* 2 y)))
               (draw-circle x y center-x center-y tiles))
        (recur (dec x) y (+ radiusError (* 2 (- y (inc x))))
               (draw-circle x y center-x center-y tiles)))
      tiles)))

(defn round-room-gen
  [center-x center-y radius]
  (fill-room (ring-room-gen center-x center-y radius)))
