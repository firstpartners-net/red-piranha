[when]There is a Cell = $cell: Cell()
[when]-unmodified = modified==false
[when]-modified = modified==true
[then]Log the cell contents = log.info("Cell value:"+$cell);