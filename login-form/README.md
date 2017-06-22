# Login Form
This sample project intent to demonstrate how one can configure a project with a HTML form as _Authentication Mechinism_.

## Basic Steps
In order to make the whole application protected by a mechanism there are a few thing to keep in mind.

### Enable static resources routing
As middleware focused on micro-services, Kikaha does not activate its Static Resources Routing module by default.
So, lets set `server.static.enabled` to true on our our
[application.yml](https://github.com/Skullabs/kikaha-samples/blob/master/login-form/resources/conf/application.yml).

```yml
server:
  static: { enabled: true }
```
### Configure authentication endpoints
Most authentication mechanisms that comes out-of-box with Kikaha will rely on endpoints
defined on `server.auth.endpoints` entry from our `application.yml` file.

```yml
server:
  auth:
    endpoints:
      login-template: "/webapp/login.html"
      login-page: "/auth/login"
      logout-url: "/auth/logout"
      error-page: "/auth/login?failed=true"
      callback-url: "/auth/verify"
```

Let's checkout what these entries means:
- **login-page**: This is the URL that users will be redirected to every time an authentication
  is required. Also, once the 'server.smart-routes.auth' is set to 'true', Kikaha
  will automatically render the "login-template" page defined above.
- **error-page**: An URL to redirect every time a Login have failed.
- **callback-url**: The URL the Form Authentication Mechanism will wait for credentials.

The following two entries belongs to the Smart Routes' module.
- **login-template**: The template file with will be displayed every time a login page is rendered.
  Once activated, this module will automatically create a route at the URL defined by `login-page`.
- **logout-url**: If activated, Kikaha will automatically deploy a route at this URL to handle the 'Logout'.

You can activate this module by setting to **true** the entry `server.smart-routes.auth.enabled`.
```yml
server:
  smart-routes:
    auth: { enabled: true }
```

### Create an HTML form
Create the HTML form you expect to be used every time a non-logged-in user try to access a protected
resource. Remeber to place it on `/webapp/login.html`, due to the configuration above defined.

```html
<form method="post" action="/auth/verify">
  <p><input placeholder="Username" name="j_username"></p>
  <p><input type="password" name="j_password"></p>
  <p><button type="submit">OK</button></p>
</form>
```
### Create the Authentication Rules
Authentication Rules are rules that protects your resources from non-authentication (or non-authorizated)
users. On the below snippet extracted from our application.yml file, we defined that every request
which URL matches the patter `/*` will be protected by a mechanism named `form`. Once no Identity Manager
is defined, it will use the default one (the Fixed Credentials Identity Manager, which alias is also named
"default").

```yml
server:
  auth:
    rules:
      - { pattern: "/*", auth-mechanisms: [ "form" ] }
```
> As stated on its [documentation](http://get.kikaha.io/docs), Kikaha is shipped with a few authentication
mechanisms and identity managers, in which "form" represents the Form Authentication Mechanism. To get more
details about how it works, please checkout [this](http://get.kikaha.io/docs/authentication-and-authorization)
guide.
