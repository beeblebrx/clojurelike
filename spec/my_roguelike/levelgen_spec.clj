(ns my_roguelike.levelgen-spec
  (:require [speclj.core :refer :all]
            [my_roguelike.levels.levelgens :as levels]))

(describe "Level generator"
          (it "should generate levels with a tile buffer that has tiles in it"
              (should (> (count (:tilebuffer (levels/level-generator))) 0)))
          (it "should give [x y] keys to tiles in the buffer"
              (should (vector? (key (first (:tilebuffer (levels/level-generator))))))
              (should= 2 (count (key (first (:tilebuffer (levels/level-generator)))))))
          (it "should represent a tile in the buffer with a Tile record"
              (should (instance? my_roguelike.levels.roomgens.Tile
                                 (val (first (:tilebuffer (levels/level-generator))))))))
