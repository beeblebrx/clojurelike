(ns my_roguelike.core-spec
  (:require [speclj.core :refer :all]
            [my_roguelike.core :refer :all]))

(describe "Game"
          (it "should start from turn 1"
              (should= 1 (:turn (start-game))))
          (it "should increase turn count by 1 when next-turn is called"
              (should= 2 (:turn (next-turn (start-game)))))
          (it "should have a world generated at start"
              (should-not (nil? (:world (start-game))))))

(describe "World generator"
          (it "should generate the given number of levels in the world"
              (should= 7 (count (generate-world 7))))
          (it "should not generate any levels if 0 is given as a parameter"
              (should= 0 (count (generate-world 0)))))

(describe "Level generator"
          (it "should generate levels with a tile buffer that has tiles in it"
              (should (> (count (:tilebuffer (level-generator))) 0)))
          (it "should give [x y] keys to tiles in the buffer"
              (should (vector? (key (first (:tilebuffer (level-generator))))))
              (should= 2 (count (key (first (:tilebuffer (level-generator)))))))
          (it "should represent a tile in the buffer with a Tile record"
              (should (instance? my_roguelike.core.Tile (val (first (:tilebuffer (level-generator))))))))

(describe "Square room generator"
          (it "should create a square room at given coords with the given size"
              (let [start-x 12
                    start-y 7
                    room ((square-room-gen start-x start-y 7))]
                (should= 49 (count room))
                (doseq [x (range start-x (+ start-x 7))
                        y (range start-y (+ start-y 7))]
                  (should (get room [x y])))))
          (it "should surround the room with walls"
              (let [room ((square-room-gen 0 0 3))]
                (doseq [x (range 3)
                        y (range 3)]
                  (if (= x y 1)
                    (should= :floor (:type (get room [x y])))
                    (should= :wall (:type (get room [x y]))))))))

(describe "Level drawing"
          (it "should draw floor tiles with '.' and walls with '#'"
              (doall (map #(let [type (:type (val %))
                                 glyph (get-glyph type)]
                             (if (= type :floor)
                               (should= "." glyph)
                               (should= "#" glyph)))
                          (:tilebuffer (level-generator))))))
