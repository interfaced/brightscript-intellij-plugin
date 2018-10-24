k = 0
while k = 0
	k = 1
	print "loop once"
end while

while true
	print "loop once"
	if k <> 0 then exit while
end while

while True
	if type(dlgMsg) = "roMessageDialogEvent"
		if dlgMsg.isButtonPressed()
			if dlgMsg.GetIndex() = 1
				closeChannel = true
			end if
			exit while
		else if dlgMsg.isScreenClosed()
			exit while
		end if
	end if
end while