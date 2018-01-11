# JWT Sample Application
A working sample microservice that uses JWT as Authentication Mechanism.

## Running the sample application
```shell
# the maven way
$ mvn clean package kikaha:run
```

## Understanding the JWT lifecycle
```shell
# to authenticate you must send you actual credentials (username and password)
# for the sake of simplicity, this sample application expects "admin" as both username and password
$ curl -X POST -d '{"username":"admin", "password":"admin"}' -H "Content-Type: application/json" http://localhost:9000/api/json/verify
{"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJtaW5pbXVtLWFjY2Vzcy1yb2xlIiwiYWRtaW4iXSwiaXNzIjoiaXNzdWVyIiwiaWF0IjoxNTE1NjI5NTY2LCJleHAiOjE1MTU2Mjk2MjZ9.ZsCIN5-PE1xhORFKzz16p_snJXVuOmb8nL1QpwWh_xmRgeXLwzIIjpm1KAgyTlEVqmSvk2Q5rc5InKlx2B0AJQ","refreshToken":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJSRUZSRVNIX1RPS0VOIl0sImlzcyI6Imlzc3VlciIsImlhdCI6MTUxNTYyOTU2NiwiZXhwIjoxNTE1NjMwMTY2LCJqdGkiOiJlMjc1ODU2NS1iZjg4LTQ5NjEtYjU1NS1iODQ2ODFjMTZlOGUifQ.ahPEgCaNuogLk8W2fa1USpSZaO6pzcWVkcQt7JXGD9wnpten0wIg1DIQ1y-khGgGOh6F1ZXEO01dSfc7IjtYHg"}

# it will print something like the above lines...
# Two important tokens were generated, token and refreshToken.
$ TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJtaW5pbXVtLWFjY2Vzcy1yb2xlIiwiYWRtaW4iXSwiaXNzIjoiaXNzdWVyIiwiaWF0IjoxNTE1NjI5NTY2LCJleHAiOjE1MTU2Mjk2MjZ9.ZsCIN5-PE1xhORFKzz16p_snJXVuOmb8nL1QpwWh_xmRgeXLwzIIjpm1KAgyTlEVqmSvk2Q5rc5InKlx2B0AJQ"
$ REFRESH="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJSRUZSRVNIX1RPS0VOIl0sImlzcyI6Imlzc3VlciIsImlhdCI6MTUxNTYyOTU2NiwiZXhwIjoxNTE1NjMwMTY2LCJqdGkiOiJlMjc1ODU2NS1iZjg4LTQ5NjEtYjU1NS1iODQ2ODFjMTZlOGUifQ.ahPEgCaNuogLk8W2fa1USpSZaO6pzcWVkcQt7JXGD9wnpten0wIg1DIQ1y-khGgGOh6F1ZXEO01dSfc7IjtYHg"

# For any subsequent request you intent to make, you should include the Token as a header.
$ curl -H "X-Authorization: Bearer $TOKEN" http://localhost:9000/api/me
{"roles":["minimum-access-role","admin"],"principal":{"name":"admin"}}

# Just in case your token has expided, you can always get a new one using your Refresh Token
curl -H "X-Authorization: Bearer $REFRESH" http://localhost:9000/api/token/create
{"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlcyI6WyJSRUZSRVNIX1RPS0VOIl0sImlzcyI6Imlzc3VlciIsImlhdCI6MTUxNTYzMDA0MiwiZXhwIjoxNTE1NjMwMTAyfQ.JA5xS2t8tTbUSYHJ7rJ6gRJzx-Rwj7vfDj2Dc7scKoMfZKu7WMlyfwTYNYrKg4_xoA-udjOVEksrfvS3XK5Y5w"}
```

