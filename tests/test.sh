#!/bin/bash
# Script to test obfuscated lua programs
# Runs original lua programs listed in test_programs.txt and
# an obfuscated version of the lua program comparing the output,
# program size and execution time. 
#
# Upon fail it prints the diff and places the obfuscated version 
# of the program in failed_test_#.lua

green='\e[0;32m'
red='\e[0;31m'
endcolor='\e[0m'
total_count=0
passed_count=0
failed_tests=""

for prog in $(cat test_programs.txt) ; do
	((total_count++))
	echo -e "${green}*********************************"
	echo -e "**       Starting Test $total_count       **"
	echo -e "*********************************${endcolor}"
	luaparse --scope $prog | luamin -a > obfuscated.lua

	echo ''
	echo "$prog"
	echo ''

	echo "Program Size Check"
	prog_size=$(stat -c%s "$prog")
	obfuscated_size=$(stat -c%s "obfuscated.lua")
	echo "Original: $prog_size bytes"
	echo "Obfuscated: $obfuscated_size bytes"
	
	echo ''
	echo '**Running original**'
	#cat $prog
	time lua $prog > output.txt

	echo '**Running obfuscated**'
	#cat obfuscated.lua
	time lua obfuscated.lua > obfuscated_output.txt

	diff=$(diff output.txt obfuscated_output.txt) 
	if [ "$diff" != "" ] 
	then
    		echo -e "${red}*********************************"
		echo -e "**           FAILED            **"
		echo -e "$**********************************${endcolor}"
		echo "DIFF:"
		echo "$diff"
		mv obfuscated.lua failed_test_${total_count}.lua
		failed_tests="$failed_tests $total_count"
	else 
		echo -e "${green}*********************************"
		echo -e "**           PASSED            **"
		echo -e "*********************************${endcolor}"
		echo ''
		((passed_count++))
	fi

done;

echo '----------TEST RESULTS-----------'

if [ "$failed_tests" != "" ] 
then
	echo -e "${red}*********************************"
	echo -e "**       $passed_count/$total_count Tests Passed      **"
	echo -e "**        Test$failed_tests Failed        **"
	echo -e "$**********************************${endcolor}"
else
	echo -e "${green}*********************************"
	echo -e "**      All Tests Passed       **"
	echo -e "*********************************${endcolor}"
fi

#cleanup
rm output.txt obfuscated_output.txt obfuscated.lua
