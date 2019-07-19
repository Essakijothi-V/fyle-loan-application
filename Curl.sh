#!/bin/sh

echo "--- API TO AUTHENTICATE USER user=essakijothi_v ---"
auth_output=$(curl -i -X POST  -H "Content-Type: application/json" https://fyle-loan-app.herokuapp.com?user=essakijothi_v)
echo "$auth_output"

token=`echo $auth_output | awk -F'token":"' '{print $2}'| sed -e 's/".*//'`
user_token=`echo $token | sed 's/\ //g'`

temp_user=`echo $auth_output | awk -F'user":"' '{print $2}'| sed -e 's/".*//'`
user=`echo $temp_user | sed 's/\ //g'`

echo "--- API TO RETRIEVE BANK DETAILS BASED ON IFSC CODE ---"
echo "IFSC=ABHY0065003. Code is unique. No need for limit"
result_ifsc=`curl -i -H "Content-Type: application/json"  -H "token:$user_token" -H "user:$user" https://fyle-loan-app.herokuapp.com/getByIfscCode?code=ABHY0065003` 
echo "$result_ifsc"

echo "--- API TO RETRIEVE BANK DETAILS BASED ON BANK NAME, CITY, OFFSET, COUNT ---"
echo "BANK_NAME=ABHYUDAYA COOPERATIVE BANK LIMITED, CITY=MUMBAI, OFFSET=1, COUNT=2"
result_bank_city=`curl -i -H "Content-Type: application/json"  -H "token:$user_token" -H "user:$user" "https://fyle-loan-app.herokuapp.com/getBranchesByBankNameCity?bankName=ABHYUDAYA%20COOPERATIVE%20BANK%20LIMITED&city=MUMBAI&offset=1&count=2"`
echo "$result_bank_city"

echo "--- API TO RETRIEVE BANK DETAILS BASED ON BANK NAME, CITY ---"
echo "BANK_NAME=ABHYUDAYA COOPERATIVE BANK LIMITED, CITY=MUMBAI"
result_bank_city_offcet_count=`curl -i -H "Content-Type: application/json"  -H "token:$user_token" -H "user:$user" "https://fyle-loan-app.herokuapp.com/getBranchesByBankNameCity?bankName=ALLAHABAD%20BANK&city=LAHARPUR"`
echo "$result_bank_city_offcet_count"
