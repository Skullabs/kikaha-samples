# HTTP2 and Protobuf Sample

## Generating a LetsEncrypt certificate
```shell
$ keytool -genkeypair -alias simple-cert -keyalg RSA -keysize 2048 -keystore letsencrypt.jks -dname "CN=app.localdomain" -storepass 123456
Enter key password for <simple-cert>
  (RETURN if same as keystore password):  
Re-enter new password: 
$ sudo certbot certonly --staging --manual --csr h2.kikaha.io.csr --preferred-challenges "dns"
Saving debug log to /var/log/letsencrypt/letsencrypt.log
Performing the following challenges:
dns-01 challenge for h2.kikaha.io

-------------------------------------------------------------------------------
NOTE: The IP of this machine will be publicly logged as having requested this
certificate. If you're running certbot in manual mode on a machine that is not
your server, please ensure you're okay with that.

Are you OK with your IP being logged?
-------------------------------------------------------------------------------
(Y)es/(N)o: y

-------------------------------------------------------------------------------
Please deploy a DNS TXT record under the name
_acme-challenge.h2.kikaha.io with the following value:

lRiZYDNBlecFChjJttUjpz2_kWbH3qFVIUxmGhOgFNE

Once this is deployed,
-------------------------------------------------------------------------------
Press Enter to Continue
Waiting for verification...
Cleaning up challenges
Server issued certificate; certificate written to /home/kikaha-samples/http2-protobuf/resources/0000_cert.pem
Cert chain written to <fdopen>
Cert chain written to <fdopen>

IMPORTANT NOTES:
 - Congratulations! Your certificate and chain have been saved at
   /home/kikaha-samples/http2-protobuf/resources/0001_chain.pem.
   Your cert will expire on 2017-10-10. To obtain a new or tweaked
   version of this certificate in the future, simply run certbot
   again. To non-interactively renew *all* of your certificates, run
   "certbot renew"

$ mv 0000_cert.pem h2.kikaha.io.pem
$ mv 0001_chain.pem h2.kikaha.io.chain-pem
$ keytool -importcert -alias simple-cert -keystore letsencrypt.jks -storepass 123456 -file h2.kikaha.io.chain-pem
```
## Configuring the letsencrypt.jks as your Kikaha's app certificate
```yml
server:
  https:
    enabled: true
    keystore: "letsencrypt.jks"
    password: "123456"
```
