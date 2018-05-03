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
    (OPER 4 Mov [(r 5)]  [(r 10)])
    (OPER 5 EQ [(r 12)]  [(r 5)(r 11)])
    (OPER 6 BEQ []  [])
  )
  (BB 4
    (OPER 7 Load [(r 14)]  [(s a)])
    (OPER 8 Mov [(r 14)]  [(r 13)])
  )
  (BB 6
    (OPER 12 Mov [(r 7)]  [(r 17)])
    (OPER 13 Mov [(r 9)]  [(r 18)])
    (OPER 14 LTE [(r 20)]  [(r 9)(r 19)])
    (OPER 15 BEQ []  [])
  )
  (BB 7
  )
)
