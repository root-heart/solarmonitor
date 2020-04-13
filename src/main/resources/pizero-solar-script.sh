#!/bin/bash
echo "Switching to charge controller WLAN"

cp /etc/wpa_supplicant/wpa_solar.conf /etc/wpa_supplicant/wpa_supplicant.conf

dhclient -r -q wlan0
wpa_cli -i wlan0 reconfigure
dhclient -v -q wlan0

echo "Fetching data from charge controller"

time_in_seconds=$(date +%s)

registers_3100_to_3107=$(printf '\x01\x04\x31\x00\x00\x08\xFF\x30' | netcat -w 1 11.11.11.254 8088 | od -A n -t x1)
registers_3100_to_3107=$(printf "$registers_3100_to_3107" | tr -d ' ' | tr -d '\n')

registers_310c_to_3111=$(printf '\x01\x04\x31\x0C\x00\x06\xBE\xF7' | netcat -w 1 11.11.11.254 8088 | od -A n -t x1)
registers_310c_to_3111=$(printf "$registers_310c_to_3111" | tr -d ' ' | tr -d '\n')

registers_3300_to_330b=$(printf '\x01\x04\x33\x00\x00\x0C\xFF\x4B' | netcat -w 1 11.11.11.254 8088 | od -A n -t x1)
registers_3300_to_330b=$(printf "$registers_3300_to_330b" | tr -d ' ' | tr -d '\n')

registers_330c_to_3313=$(printf '\x01\x04\x33\x0C\x00\x08\x3E\x8B' | netcat -w 1 11.11.11.254 8088 | od -A n -t x1)
registers_330c_to_3313=$(printf "$registers_330c_to_3313" | tr -d ' ' | tr -d '\n')

echo "Switching back to regular WLAN"

cp /etc/wpa_supplicant/wpa_wlan.conf /etc/wpa_supplicant/wpa_supplicant.conf
dhclient -r -q wlan0
wpa_cli -i wlan0 reconfigure
dhclient -v -q wlan0

echo "Posting data ($time_in_seconds: $registers_3100_to_3107, $registers_310c_to_3111, $registers_3300_to_330b, $registers_330c_to_3313)..."

curl -H "Content-Type: application/json" --request POST \
  --data "{\"secondsSince1970\": $time_in_seconds, \"registers3100To3107\": \"$registers_3100_to_3107\", \"registers310cTo3111\": \"$registers_310c_to_3111\", \"registers3300To330b\": \"$registers_3300_to_330b\", \"registers330cTo3313\": \"$registers_330c_to_3313\"}" \
   https://root-heart-solarmonitor.herokuapp.com/power

echo "Data posted, script finished :)"
