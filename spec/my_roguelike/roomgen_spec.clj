(ns my_roguelike.roomgen-spec
  (:require [speclj.core :refer :all]
            [my_roguelike.levels.roomgens :as rooms]))

(describe "Square room generator"
          (it "should create a square room at given coords with the given size"
              (let [start-x 12
                    start-y 7
                    room (rooms/square-room-gen start-x start-y 7)]
                (should= 49 (count room))
                (doseq [x (range start-x (+ start-x 7))
                        y (range start-y (+ start-y 7))]
                  (should (get room [x y])))))
          (it "should create no tiles if size is 0"
              (should= 0 (count (rooms/square-room-gen 0 0 0))))
          (it "should create no tiles if size is negative"
              (should= 0 (count (rooms/square-room-gen 1 1 -1)))))

(describe "Rectangular room generator"
          (it "should create a rectangular room at given coords with given width and height"
              (let [room-x 3
                    room-y 5
                    room (rooms/rectangle-room-gen room-x room-y 2 4)]
                (should= 8 (count room))
                (doseq [x (range room-x (+ room-x 2))
                        y (range room-y (+ room-y 4))]
                  (should (get room [x y])))))
          (it "should create no tiles if width or height is 0"
              (should= 0 (count (rooms/rectangle-room-gen 2 2 10000 0)))
              (should= 0 (count (rooms/rectangle-room-gen 3 3 0 10000))))
          (it "should create no tiles if width or height is negative"
              (should= 0 (count (rooms/rectangle-room-gen 1 1 -1 1)))
              (should= 0 (count (rooms/rectangle-room-gen 1 1 1 -1)))))

(describe "Ring room generator"
          (it "should create a room (or corridor) the shape of ring"
              (let [room (rooms/ring-room-gen 0 0 2)
                    coords [[2 -1] [0 -2] [1 1] [-1 2] [-1 -1] [-1 -2] [-2 1]
                            [-2 -1] [1 -1] [0 2] [-1 1] [2 0] [2 1] [1 2]
                            [1 -2] [-2 0]]]
                (should= 16 (count room))
                (doseq [tile room]
                  (should (some #(= % (key tile)) coords))
                  (should= :floor (:type (val tile))))))
          (it "should create just one tile to [0 0] if radius is 0"
              (should= 1 (count (rooms/ring-room-gen 0 0 0)))
              (should= [0 0] (first (keys (rooms/ring-room-gen 0 0 0))))))

(describe "Wall builder"
          (it "should create walls around a square room"
              (let [room (rooms/square-room-gen 0 0 3)]
                (doseq [x (range 3)
                        y (range 3)]
                  (should= :floor (:type (get room [x y]))))
                (let [walled-room (rooms/build-walls room)]
                  (doseq [x (range -1 4)
                          y (range -1 4)]
                    (if (or (or (= x -1) (= x 3))
                            (or (= y -1) (= y 3)))
                      (should= :wall (:type (get walled-room [x y])))
                      (should= :floor (:type (get walled-room [x y]))))))))
          (it "should not implode when asked to wall a room without tiles"
              (rooms/build-walls (rooms/square-room-gen 0 0 0))))
