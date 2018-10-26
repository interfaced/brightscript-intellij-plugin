if true<fold text='...'> print "fold" else print "even"</fold>
if predicate() then<fold text='...'> print "fold" else print "even"</fold>

if a and b then<fold text='...'>
    print "foo"
else if a and not b<fold text='...'>
    print "bar"</fold>
elseif not a then<fold text='...'>
    print "baz"</fold>
else<fold text='...'>
    print "foo"
    print "bar"
    print "baz"</fold>
</fold>end if