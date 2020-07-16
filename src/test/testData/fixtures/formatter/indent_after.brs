sub main()
    for i = 0 to 10
        my(i)
    next
end sub

function my(x)
    a = [
        1,
        2,
        [
            1,
            2,
            {
                a: 1,
                d: {
                    a: 1,
                    b: 2, c: 3,
                    d: 4,
                    e: {
                    }
                }
            }
        ]
    ]

    for each a in b
        while a <> true
            if a = 5
                print a
            else if a < 5 then
                print b
                a = function(x, y)
                    return x + y
                endfunction
                b = sub(input)
                    print input
                end sub
            else
                for a = 5 to 10 step -1
                    print "!"
                endfor
                exitwhile
            endif
        endwhile
    endfor
endfunction