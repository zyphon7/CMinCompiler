(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 6 Add_I [(r 4)]  [(r 1)(r 2)])
    (OPER 7 Mov [(r 3)]  [(r 4)])
    (OPER 8 Mov [(m RetReg)]  [(r 3)])
    (OPER 9 Jmp []  [(bb 4)])
  )
  (BB 5
  )
  (BB 4
    (OPER 4 Func_Exit []  [])
    (OPER 5 Return []  [(m RetReg)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 6 Mov [(r 1)]  [(i 5)])
    (OPER 7 EQ [(r 6)]  [(r 1)(i 5)])
    (OPER 8 BEQ []  [(r 6)(i 0)(bb 7)])
  )
  (BB 5
    (OPER 9 Store []  [(i 3)(s a)(i 0)])
  )
  (BB 6
    (OPER 12 Mov [(r 3)]  [(i 0)])
    (OPER 13 Mov [(r 5)]  [(i 1)])
    (OPER 14 LTE [(r 7)]  [(r 5)(i 8)])
    (OPER 15 BEQ []  [(r 7)(i 0)(bb 9)])
  )
  (BB 8
    (OPER 16 Add_I [(r 8)]  [(r 3)(r 5)])
    (OPER 17 Mov [(r 3)]  [(r 8)])
    (OPER 18 Add_I [(r 9)]  [(r 5)(i 1)])
    (OPER 19 Mov [(r 5)]  [(r 9)])
    (OPER 20 LTE [(r 10)]  [(r 5)(i 8)])
    (OPER 21 BNE []  [(r 10)(i 0)(bb 8)])
  )
  (BB 9
    (OPER 22 Div_I [(r 11)]  [(r 3)(i 3)])
    (OPER 23 Mov [(r 4)]  [(r 11)])
    (OPER 24 Mul_I [(r 12)]  [(r 4)(i 4)])
    (OPER 25 Mov [(r 3)]  [(r 12)])
    (OPER 26 Load [(r 13)]  [(s a)(i 0)])
    (OPER 27 Pass []  [(r 13)] [(PARAM_NUM 0)])
    (OPER 28 Pass []  [(r 1)] [(PARAM_NUM 1)])
    (OPER 29 JSR []  [(s addThem)] [(numParams 2)])
    (OPER 30 Mov [(r 14)]  [(m RetReg)])
    (OPER 31 Mov [(r 2)]  [(r 14)])
    (OPER 32 Add_I [(r 15)]  [(r 2)(r 3)])
    (OPER 33 Pass []  [(r 15)] [(PARAM_NUM 0)])
    (OPER 34 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 35 Mov [(r 16)]  [(m RetReg)])
    (OPER 36 Pass []  [(i 12)] [(PARAM_NUM 0)])
    (OPER 37 JSR []  [(s putchar)] [(numParams 1)])
    (OPER 38 Mov [(r 17)]  [(m RetReg)])
    (OPER 39 Mov [(m RetReg)]  [(i 0)])
    (OPER 40 Jmp []  [(bb 4)])
  )
  (BB 10
  )
  (BB 4
    (OPER 4 Func_Exit []  [])
    (OPER 5 Return []  [(m RetReg)])
  )
  (BB 7
    (OPER 10 Store []  [(i 4)(s a)(i 0)])
    (OPER 11 Jmp []  [(bb 6)])
  )
)
