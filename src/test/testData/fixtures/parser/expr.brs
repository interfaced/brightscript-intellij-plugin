' or expr
true OR false Or a or b
' and expr
a and b And c AND d
' comp expr
1 > 2 < foo = bar >= baz <= 42! <> c
' bit shift expr
a >> b << c
' add expr
a + b - c
' mul expr
a * c / b \ d mod 2
' unary expr
-a : +b : --a : ++b : not false
' exponential expr
a ^ b
' member ref expr
foo.bar.baz
foo@bar.baz
' index access expr
foo.bar["baz"]
' anon function expr
a = function(x, y) : return x + y : endfunction
b = function(x, y)
	return x - y
endFunction
' call expr
baz()
foo.baz(42)
foo.baz["bar"](a, b)
' identifier ref expr
myvar$
mydouble#
' paren expr
()
((a + b) * (b - a)) ^ (d mod 2 * not a)