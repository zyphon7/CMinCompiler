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
    (OPER 8 BEQ []  [(r 12)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 9 Mov [(r 13)]  [(i 3)])
    (OPER 10 Store []  [(r 13)(r a)(i 0)])
  )
  (BB 6
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
    (OPER 14 Mov [(r 15)]  [(i 0)])
    (OPER 15 Mov [(r 7)]  [(r 15)])
    (OPER 16 Mov [(r 16)]  [(i 1)])
    (OPER 17 Mov [(r 9)]  [(r 16)])
    (OPER 18 Mov [(r 17)]  [(i 8)])
    (OPER 19 LTE [(r 18)]  [(r 9)(r 17)])
    (OPER 20 BEQ []  [])
  )
  (BB 7
  )
)
