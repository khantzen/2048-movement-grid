(ns move-2048.grid-move-test
    (:require [clojure.test :refer :all]
              [move-2048.grid-move :refer :all]))
  
(deftest should-move-grid-to-the-right
    (testing "Empty grid should be left empty"
       (is (= [] (move-right [] ))))

    (testing "Single line grid"

        (testing "One element grid should be left unchanged"
            (is (= [[2]] (move-right [[2]]))))
            
        (testing "Single line element should be pushed to the right"
            (are [x y] (= x y)
                [[nil nil nil 2]] (move-right [[nil nil 2 nil]] )
                [[nil nil nil 4]] (move-right [[nil 4 nil nil]] )
                [[nil nil]]       (move-right [[nil nil]])
            ))
            
        (testing "Unidentical values should stay in place"
            (are [x y] (= x y)
                [[2 4]] (move-right [[2 4]])
                [[nil nil 2 4]] (move-right [[2 nil 4 nil]])
            ))

        (testing "Identical values should be merged"
            (are [x y] (= x y)
                [[nil 4]] (move-right [[2 2]])
                [[nil 2 4]] (move-right [[2 2 2]]) 
            ))
    )
    (testing "Multiple line grid"
        (are [x y] (= x y)
            [[nil nil 4]
             [nil 2   8]
             [nil 4   4]] (move-right [[2 2 nil] [2 4 4] [2 2 4]]
        )
    ))
)

(deftest should-move-grid-to-the-left
    (testing "Multiple line grid")
        (are [x y] (= x y)
            [[nil nil nil nil]
             [4   nil nil nil]
             [16  4   nil nil]] (move-left [[nil nil nil nil] [2 nil nil 2] [8 8 2 2]])
        
        )
    )

(deftest should-move-grid-to-the-top
    (testing "Multiple line grid")
        (are [x y] (= x y)
            [[4   4   8  ]
             [nil 2   2  ]
             [nil nil nil]] (move-top [[2 2 4] [nil 2 4] [2 2 2]])
        
        )
)

(deftest should-move-grid-to-the-bot
    (testing "Multiple line grid")
        (are [x y] (= x y)
            [[nil  nil   nil]
             [nil  2     8  ]
             [4    4     2 ]] (move-down [[2 2 4] [nil 2 4] [2 2 2]])
        
        )
)