
# Find exact name
[condition][net.firstpartners.data.Cell]there is a document value with name {name}=$cell : Cell(name=="{name}")

# test for name starting
[condition][net.firstpartners.data	.Cell]there is a document value starting with name {name}=$cell : Cell(getNameStartsWith("{name}"))

#update name
[consequence][]use this name instead {outname}=$cell.setName("{outname}");log.info("Updating cell Name to {outname} value:"+$cell.getValue());



