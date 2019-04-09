#!/usr/bin/env sh

cd MovieApp

# open project in vs code
code ./

# start android emulator
~/Library/Android/sdk/emulator/emulator -avd Pixel_2_XL_API_28 &
ionic cordova run android --emulator

# run in browser
ionic serve
