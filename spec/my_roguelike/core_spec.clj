(ns my_roguelike.core-spec
  (:require [speclj.core :refer :all]
            [my_roguelike.core :refer :all]
            [my_roguelike.levels.levelgens :as levels]))

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

(describe "Level drawing"
          (it "should draw floor tiles with '.' and walls with '#'"
              (doall (map #(let [type (:type (val %))
                                 glyph (get-glyph type)]
                             (if (= type :floor)
                               (should= "." glyph)
                               (should= "#" glyph)))
                          (:tilebuffer (levels/level-generator))))))
