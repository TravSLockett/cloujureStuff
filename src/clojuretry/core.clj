(ns clojuretry.core
  (:require [clojure.string :as str])
  (:gen-class))
(use 'clojure.java.io)

(defmacro discount ([cond dis1 dis2]
                    (list `if cond dis1 dis2)))
(defmacro reg-math
  [calc]
  (list (second calc) (first calc) (nth calc 2)))
(defmacro do-more [cond & body]
  (list `if cond (cons 'do body)))
(defmacro do-more-2 [cond & body] `(if ~cond (do ~@body)))
(comment ;structure map
  (defn struct-map-ex [] (defstruct Customer :Name :Phone)
    (def cust1 (struct Customer "Dough" 123123))
    (def cust2 (struct-map Customer :Name "Sally" :Phone 4423434))
    (println cust1)
    (println (:Name cust2))))

(comment ;; destrucring
  (defn destruct [] (def vectVals [1 2 3 4])
    (let [[one two & the-rest] vectVals]
      (println one two the-rest))))

(comment ;;file ops
  (defn write-to-file [file text]
    (with-open [wrtr (writer file)]
      (.write wrtr text)))
  (defn read-from-file [file] (try (println (slurp file))
                                   (catch Exception e (println " Error: " (.getMessage e)))))

  (defn append-to-file [file text] (with-open [wrtr (writer file :append true)]
                                     (.write wrtr text)))
  (defn read-line-from-file [file] (with-open [rdr (reader file)]
                                     (doseq [line (line-seq rdr)]
                                       (println line)))))

(comment ;;loop
  (defn one-to-x [x] (def i (atom 1))
    (while (<= @i x)
      (do (println @i)
          (swap! i inc))))

  (defn dbl-to-x [x] (dotimes [i x] (println (* i 2))))
  (defn triple-to-x [x y] (loop [i x]
                            (when (< i y) (println (* i 3))
                                  (recur (+ i 1)))))

  (defn print-list [& nums] (doseq [x nums]
                              (println x))))

(comment ;;conditional statments
  (defn can-vote
    [age]
    (if (>= age 18)
      (println "you can vote")
      (println "you cannot vote")))

  (defn can-do-more [age] (if (>= age 18) (do (println "you can drive") (println "you can vote"))
                              (println " you cant vote")))

  (defn when-ex [tof] (when tof (println "1st thing") (println "2nd thing")))

  (defn what-grade [n] (cond (< n 5) (println "preschool") (= n 5) (println "kindergarden") (and (> n 5) (<= n 18)) (format " go to grade %d" (- n 5)) :else "Go to college")))

(comment ;;simple functions
  (defn say-hello "this is doing a soemthing" [name]
    (println " hello again" name))
  (defn get-sum [x y] (println (+ x y)))
  (defn get-sum-more ([x y z] (println (+ x y z)))
    ([x y] (+ x y)))
  (defn hello-you [name] (println (str "Hello " name)))
  (defn hello-all [& names] (map hello-you names)))

(comment ;;math
  (defn math-stuff [] (println (+ 1 2 3))
    (println (- 5 3 2))
    (println (/ 10 5))
    (println (* 10 5))
    (println (mod 10 5))
    (println (inc 5));;increment
    (println (dec 10));;decrement
    (println (Math/abs -10)) ;;absolute value
    (println (Math/cbrt 8));;cube root
    (println (Math/sqrt 4)) ;; squre root
    (println (Math/ceil 4.5)) ;; round up
    (println (Math/floor 4.5)) ;; round down
    (println (Math/exp 1));; e to the power of 1 
    (println (Math/hypot 2 2));; sqrt (x^2 + y^2)
    (println (Math/log 2.718));; natural log
    (println (Math/log10 100));; base 10 log
    (println (Math/max 1 5))
    (println (Math/min 1 5))
    (println (Math/pow 2 2));; power
    (println (rand-int 20))
    (println (reduce + [1 2 3]))
    (println (Math/PI))))

(comment
;;long
  (def random 10)
;;double
  (def aDouble 1.2233))

(comment ;;atom to change value
  (defn atom-ex
    [x]
    (def atomEx (atom x))
    (add-watch atomEx :watcher (fn [key atom old-state new-state]
                                 (println "atomEx changed from " old-state " to " new-state)))
    (println "1st x" @atomEx)
    (reset! atomEx 10)
    (println "2nd x" @atomEx)
    (swap! atomEx inc)
    (println "Increment x" @atomEx)))
(comment agent (defn agent-ex [] (def tickets-sold (agent 0)) (send tickets-sold + 15) (println)
                 (println "Tickets " @tickets-sold) (send tickets-sold + 10) (await-for 100 tickets-sold)
                 (println "Tickets " @tickets-sold) (shutdown-agents)))

(defn -main

  [& args]
  (discount (> 25 65) (println "10% off") (println "Full price"))
  (reg-math (2 + 5))
  (do-more (< 1 2) (println "hello"))
  (do-more-2 (< 1 2) (println "hello"))
  ;(struct-map-ex)
  ;(destruct)
  ;(write-to-file "test.txt" "this is a sentence\n")
  ;(read-from-file "test.txt")
  ;(append-to-file "test.txt" "this is another sentence\n")
  ;(read-line-from-file "test.txt")
  ;(one-to-x 5)
  ;(dbl-to-x 5)
  ;(triple-to-x 1 5)
  ;(print-list 7 8 9)
  ;(can-vote 17)
  ;(can-do-more 24)
  ;(when-ex true)
  ;(what-grade 19)
  ;(say-hello "Derek")
;(get-sum 4 5)
  ;(get-sum-more 1 2 3)
  ;(hello-all "Doubg" "Mary" "Paul")
  ;(math-stuff)
  ;(agent-ex)
  ;(atom-ex 5)
  (comment ;;formatting output

    (def aString "hello")
    (def aLong 15)
    (def aDouble 1.234)
    (format "this is a string %s" aString)
    (format "5 and spaces %5d" aLong)
    (format "leading zerores %04d" aLong)
    (format "%-4d left justified" aLong)
    (format "3 decimals %.3f" aDouble))

  (comment ;;string stuff
    (def str1 "this is my 2nd string")
    (str/blank? str1)
    (str/includes? str1 "my")
    (str/index-of str1 "m")
    (str/split str1 #" ")
    (str/split str1 #"\d")
    (str/join " " ["The", "Big", "Cheese"])
    (str/replace "I am 42" #"42" "43")
    (str/upper-case str1)
    (str/lower-case str1))

  (comment ;;list
    (println (list "Dog" 1 3.4 true))
    (println (first (list 1 2 3)))
    (println (list* 1 2 [3 4]))
    (println (cons 3 (list 1 2))))

  (comment ;;set
    (println (set '(1 1 2)))
    (println (get (set '(1 2)) 2))
    (println (conj (set '(3 2)) 1))
    (println (contains? (set '(3 2)) 2))
    (println (disj (set '(3 2)) 2)))

  (comment ;;vector
    (println (get (vector 3 2) 1))
    (println (conj (vector 3 2) 1))
    (println (pop (vector 1 3 2 4)))
    (println (subvec (vector 1 2 3 2) 1 3)))

  (comment ;;maps
    (println (hash-map "name" "derek"))
    (println (sorted-map 3 42 2 "name" 1 "derek"))
    (println (get (hash-map "name" "derek") "name"))
    (println (find (hash-map "name" "derek") "name"))
    (println (contains? (hash-map "name" "derek") "name"))
    (println (keys (hash-map "name" "derek")))
    (println (vals (hash-map "name" "derek")))
    (println (merge-with + (hash-map "name" "derek") (hash-map "Age" 42)))))
