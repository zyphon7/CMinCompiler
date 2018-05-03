(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 4)]  [(r 2)(r 3)])
    (OPER 5 Mov [(r 1)]  [(r 4)])
    (OPER 6 Mov [(m RetReg)]  [(r 1)])
    (OPER 7 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 10)]  [(i 5)])
    (OPER 5 Mov [(r 5)]  [(r 10)])
    (OPER 6 Mov [(r 11)]  [(i 5)])
    (OPER 7 EQ [(r 12)]  [(r 5)(r 11)])
    (OPER 8 BEQ []  [])
  )
  (BB 4
    (OPER 9 Mov [(r 13)]  [(i 3)])
    (OPER 10 Load [(r 14)]  [(s a)])
    (OPER 11 Mov [(r 14)]  [(r 13)])
  )
  (BB 6
    (OPER 16 Mov [(r 17)]  [(i 0)])
    (OPER 17 Mov [(r 7)]  [(r 17)])
    (OPER 18 Mov [(r 18)]  [(i 1)])
    (OPER 19 Mov [(r 9)]  [(r 18)])
    (OPER 20 Mov [(r 19)]  [(i 8)])
    (OPER 21 LTE [(r 20)]  [(r 9)(r 19)])
    (OPER 22 BEQ []  [])
  )
  (BB 7
  )
)
