#Cell Selection Rules
[when]There is a Cell = $cell: Cell()
[when]-unmodified = modified==false
[when]-modified = modified==true

# Logging rules
[then]Log the cell contents = log.addUIInfoMessage("Cell value:"+$cell);