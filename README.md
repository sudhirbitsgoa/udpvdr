# Instructions

./bin/udpVdr --help # lists interface which support mulicast
./bin/udpVdr <networkinterface> <port> <multicastip> <nmea-statement> <nullChar>

Please put NMEA statement in quotes as shown in example

```
 ./bin/udpVdr wlan2 60005 239.192.0.5 'UdPbC\s:HE0001,n:242*09\$HEHDT,5.71,T*1C'
```

```
 ./bin/udpVdr wlan2 60005 239.192.0.5 'UdPbC\s:HE0001,n:242*09\$HEHDT,5.71,T*1C' nullChar
```
# other statements

```
 ./bin/udpVdr wlan2 60005 239.192.0.5  'UdPbC\s:HE0001,n:242*09\$HEHDT,5.71,T*1C' nullChar
 ./bin/udpVdr wlan2 60005 239.192.0.5  'UdPbC\s:HE0001*45\$HEHDT,5.71,T*1C' nullChar
 ./bin/udpVdr wlan2 60005 239.192.0.5  'UdPbC\s:C90001*32\$C9TXT,1,1,10,WATCHHOUR:0000TO0100*4A' nullChar
```