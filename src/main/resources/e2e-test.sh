curl --request POST -sL \
  --url "$1/balances/ingsoc"
curl --request POST -sL \
  --url "$1/balances/winston"
curl --request POST -sL \
  --url "$1/balances/julia"

curl --request POST -sL \
  --url "$1/transactions/winston/ingsoc/100"

curl --request GET -sL \
  --url "$1/balances"

curl --request POST -sL \
  --url "$1/loans/room/julia/winston/200"

curl --request POST -sL \
  --url "$1/loans/room/repay/20"
curl --request POST -sL \
  --url "$1/loans/room/repay/10"
curl --request POST -sL \
  --url "$1/loans/room/repay/171"

curl --request GET -sL \
  --url "$1/loans"

curl --request GET -sL \
  --url "$1/balances"

