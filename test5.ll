(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Load [(r 2)]  [(s d)])
    (OPER 5 Load [(r 3)]  [(s e)])
    (OPER 6 Add_I [(r 4)]  [(r 0)(r 0)])
    (OPER 7 Mov []  [])
    (OPER 8 Mov [(r RetReg)]  [(r 1)])
    (OPER 9 Jmp []  [(bb 1)])
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
    (OPER 4 Mov []  [])
    (OPER 5 EQ [(r 12)]  [(r 5)(r 11)])
    (OPER 6 BEQ []  [])
  )
  (BB 4
    (OPER 7 Load [(r 14)]  [(s a)])
    (OPER 8 Mov []  [])
  )
  (BB 6
    (OPER 12 Mov []  [])
    (OPER 13 Mov []  [])
    (OPER 14 LTE [(r 20)]  [(r 9)(r 19)])
    (OPER 15 BEQ []  [])
  )
  (BB 7
  )
)
