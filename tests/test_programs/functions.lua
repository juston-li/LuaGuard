a = "aeiou" -- a string
b = 13      -- a number
c = function()  -- a function
print ("\n\n\tAin't it grand")
end
d = { a, b ,c} -- put them in a table
function printit(tata)  -- print their types.
table.unpack(tata) -- unpack the table
for key, value in ipairs(tata) do print(key, type(value)) end
end
printit(d)
