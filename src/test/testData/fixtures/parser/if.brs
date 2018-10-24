if x>127 then print "out of range" : end

if x > 127 then print "out of range"
If caveman = "fred" then print "flintstone" else print "rubble"

if type(msg) = "roVideoPlayerEvent" then
	if debug then print "video event"
	if msg.isFullResult()
		if debug then print "video finished"
		return 9
	endif
else if type(msg) = "roUniversalControlEvent" then
	if debug then print "button press "; msg.GetInt()
	HandleButton(msg.GetInt())
elseif msg = invalid then
	if debug print "timeout"
	return 6
else
	print ""
end if

If type(5.tostr())<> "String" Then Stop
If (-5).tostr()<>"-5" Then Stop
If (1+2).tostr()<>"3" Then Stop
If 5.tostr()<>"5" Then Stop
If i.tostr()<>"-55" Then Stop
If 100%.tostr()<>"100" Then Stop
If (-100%).tostr()<>"-100" Then Stop
If y%.tostr()<>"10" Then Stop
If "5".toint()<>5 Or type("5".toint())<>"Integer" Then Stop
If "5".tofloat()<>5.0 Or type("5".tofloat())<>"Float" Then Stop
If fs.tofloat()<>-1.1 Or fs.toint()<>-1 Then Stop
If "01234567".left(3)<>"012" Then Stop
If "01234567".right(4)<>"4567" Then Stop
If "01234567".mid(3)<>"34567" Then Stop
If "01234567".mid(3,1)<>"3" Then Stop
If "01234567".instr("56")<>5 Then Stop
If "01234567".instr(6,"56")<>-1 Then Stop
If "01234567".instr(0,"0")<>0 Then Stop