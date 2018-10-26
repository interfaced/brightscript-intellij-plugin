For x = 1 To 5
    For y = 1 To 4
        For z = 1 To 6
            c[x, y, z] = k
            k = k + 1
        End for
    End for
End for

For x = 1 To 5
    For y = 1 To 4
        For z = 1 To 6
            If c[x, y, z] <> k Then print"error" : Stop
            If c[x][y][z] <> k Then print "error": Stop
            k = k + 1
        Endfor
    End for
EndFor

For i=10 To 1 Step -1
    print i
End For

For Each n In aa
    Print n;aa[n]
    aa.delete(n)
End For