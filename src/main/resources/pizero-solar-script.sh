#!/bin/bash
echo "Switching to charge controller WLAN"

cp /etc/wpa_supplicant/wpa_solar.conf /etc/wpa_supplicant/wpa_supplicant.conf

dhclient -r -q wlan0
wpa_cli -i wlan0 reconfigure
dhclient -v -q wlan0

echo "Fetching data from charge controller"

current_state=$(printf '\x01\x04\x31\x00\x00\x08\xFF\x30' | netcat -w 1 11.11.11.254 8088 | od -A n -t x1)
current_state=$(printf "$current_state" | tr -d ' ' | tr -d '\n')

time_in_seconds=$(date +%s)

echo "Switching back to regular WLAN"

cp /etc/wpa_supplicant/wpa_wlan.conf /etc/wpa_supplicant/wpa_supplicant.conf
dhclient -r -q wlan0
wpa_cli -i wlan0 reconfigure
dhclient -v -q wlan0

echo "Posting data ($time_in_seconds: $current_state)..."

curl -H "Content-Type: application/json" --request POST --data "{\"secondsSince1970\": $time_in_seconds, \"currentPowerData\": \"$current_state\"}" https://root-heart-solarmonitor.herokuapp.com/power

echo "Data posted, script finished :)"
