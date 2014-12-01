(ns my_roguelike.levels.levelgens
  (:require [my_roguelike.levels.roomgens :as r]))

(defn square-arena
  [x y size]
  (fn [] (r/square-room-gen x y size)))

(defn round-arena
  [center-x center-y radius]
  (fn [] (r/round-room-gen center-x center-y radius)))

(defn level-generator
  ([] (level-generator (round-arena 5 5 4)))
  ([generator]
     {:tilebuffer (r/build-walls (generator))}))
