# This translator file allows us to map from a domain specific language (English-like)
# Into something the rule engine understands
#
# https://docs.jboss.org/drools/release/6.5.0.Final/drools-docs/html/ch08.html#d0e12041
#
[condition][net.firstpartners.data.Cell] There is a document value with name {cell-name} =$cell : Cell(name=="{cell-name}")
[consequence][]Add to output table as {out-name}= $cell.setName("{out-name}");
