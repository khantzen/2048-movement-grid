(ns move-2048.grid-move
    (:gen-class))

(declare move-line-left filter-non-null time-2 merge-values complete-until)

(def seq-to-vector (partial into []))
(def reverse-v (comp seq-to-vector reverse))
(defn transpose [m]
    (apply mapv vector m))

(defn reverse-grid [grid] (seq-to-vector (map reverse-v grid)))

(defn move-grid-left
    ([] [])
    ([line] [(move-line-left line)])
    ([line & next] (into [(move-line-left line)] (apply move-grid-left next) ))
)

(def move-left  (partial apply move-grid-left)) 
(def move-right (comp reverse-grid move-left reverse-grid))
(def move-top   (comp transpose move-left transpose))
(def move-down  (comp transpose move-right transpose))

(defn move-line-left "move a 2048 line to the left"
    [line]
    (let [ until-length (complete-until (count line))
           merge-array  (partial apply merge-values) ]
        (-> line
            (filter-non-null)
            (seq-to-vector)
            (merge-array)
            (until-length))
    )
)

(defn complete-until [length] "return a method that prefix a given list with nil until its length is equal to length param"
    (fn append? [list] (if (= length (count list)) list (append? (conj list nil)) )))

(defn merge-values "will sum sibbling integer in array into one"
    ([] [])
    ([single-value] [single-value])
    ([one two] (if (= one two) [(time-2 one)] [one two]))
    ([one two & tail]  (if (= one two)  (into [(time-2 one)] (apply merge-values tail)) (into [one] (apply merge-values two tail)))  )
)
    
(defn filter-non-null [list] (filter #(not (nil? %)) list))
(defn time-2 [value] (* value 2))
