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
    (OPER 4 Mov [(r 6)]  [(i 5)])
    (OPER 5 Mov [(r 1)]  [(r 6)])
    (OPER 6 Mov [(r 7)]  [(i 5)])
    (OPER 7 EQ [(r 8)]  [(r 1)(r 7)])
    (OPER 8 BEQ []  [(r 8)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 9 Mov [(r 9)]  [(i 3)])
    (OPER 10 Store []  [(r 9)(s a)(i 0)])
  )
  (BB 6
    (OPER 14 Mov [(r 11)]  [(i 0)])
    (OPER 15 Mov [(r 3)]  [(r 11)])
    (OPER 16 Mov [(r 12)]  [(i 1)])
    (OPER 17 Mov [(r 5)]  [(r 12)])
    (OPER 18 Mov [(r 13)]  [(i 8)])
    (OPER 19 LTE [(r 14)]  [(r 5)(r 13)])
    (OPER 20 BEQ []  [(r 14)(i 0)(bb 9)])
  )
  (BB 7
  )
)
