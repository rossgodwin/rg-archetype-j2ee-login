

# ExsJavaEEFormAuthWebApp

## Technologies
* Eclipse 4.5.2 Mars
* Jdk1.8.0_77
* Wildfly 10
* MySql 5.1.38
* Hibernate 5.1
* Other 3rd party libraries (i.e. Apache commons lang)

## Highlights
* Declarative form-based authentication
* Custom login module that extends jboss class **AbstractServerLoginModule**
* Password digest
* Hibernate configuration
  * **HibernateTxRequestFilter** class for session-per-request unit-of-work
  * **HibernateUtil** class for SessionFactory singleton
  * User entity and DAO classes
* Web content includes sign up and login page
* Example rest service

## Setup

### MySql
* Create new database schema.  For this project call it 'boilerplate'
* Create user table
```
CREATE TABLE `user` (`id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(45) NOT NULL, `password` longtext NOT NULL, `role` varchar(45) DEFAULT NULL, `email` varchar(100) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `username_UNIQUE` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
```

### Java EE Server - WildFly
Download and extract WildFly to {wildfly_home}

### Eclipse setup server runtime
* Go to Window -> Preferences -> Server -> Runtime Environments
* Select Add...
* Choose **WildFly 10.0 Runtime**
* Name: **WildFly 10.0 Boilerplate**
* Home Directory: browse to {wildfly_home} location and select
* Runtime JRE -> Alternate JRE: select jdk1.8.0_77

### Import project from Git repository and configure project
* Right click on ExsJavaEEFormAuthWebApp -> Build Path -> Configure Build Path...
* Under Libraries tab
* Select Add Library...
* Select Server Runtime
* Select **WildFly 10.0 Boilerplate**
* Remove JRE System Library
* Select Add Library...
* Select JRE System Library
* Choose Alternate JRE -> jdk1.8.0_77

### Eclipse setup server
* Window -> Show View -> Servers
* New -> Server
* Server type: **WildFly 10.0**
* Name: **WildFly 10.0 Boilerplate**
* Next
* Choose runtime server **WildFly 10.0 Boilerplate**
* Next
* Skip adding the application to the server configuration, you will do that later
* Start the server
  * You may run into the following error on startup.  I used a program called Tcpview to find the port that was being used.  In my case it was a NVIDIA process, NvNetworkService.exe.  Upon ending this process, the wildfly server started with any problems.

```
Failed to start service jboss.serverManagement.controller.management.http: org.jboss.msc.service.StartException in service jboss.serverManagement.controller.management.http: WFLYSRV0083: Failed to start the http-interface service
...
Caused by: java.net.BindException: Address already in use: bind
```

* open a command prompt and navigate to {wildfly_home}\bin
* run add-user.bat to setup a Management user
* Access management console http://127.0.0.1:9990
* Notice the user/password was added in {wildfly_home}\standalone\configuration\mgmt-users.properties

### Configure WildFly

#### Add jdbc module
* update {ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-jdbc-module.cli if needed
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-jdbc-module.cli
```
* creates mysql jdbc module in {wildfly_home}\modules\com\mysql\jdbc

#### Create datasource
* update {ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-datasource-module.cli if needed.  If you change the datasource name you will need to update the following:
  * {ExsJavaEEFormAuthWebApp home}\src\com\gwn\exs\ba\data\hibernate\hibernate.cfg.xml
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-datasource-module.cli
```

* creates datasource {wildfly_home}\standalone\configuration\standalone.xml
* also in the admin console -> Configuration -> Subsystems -> Datasources -> Non-XA - you should see MySqlBoilerplateDS

#### Create security domain
* update {ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-security-domain.cli if needed.  If you change the security-domain name you will need to update the following:
  * {ExsJavaEEFormAuthWebApp home}\WebContent\WEB-INF\jboss-web.xml
* open command prompt
* navigate to {wildfly_home}\bin and run the following...

```
jboss-cli.bat --file={ExsJavaEEFormAuthWebApp home}\Resources\jboss-add-security-domain.cli
```

### In Eclipses servers view
* Right-click **WildFly 10.0 Boilerplate** -> Stop
* Right-click **WildFly 10.0 Boilerplate** -> Add and Remove...
* Add **ExsJavaEEFormAuthWebApp** project
* Right-click **WildFly 10.0 Boilerplate** -> Publish
* Right-click **WildFly 10.0 Boilerplate** -> Start

### Try it
* http://127.0.0.1:8080/ExsJavaEEFormAuthWebApp
* http://127.0.0.1:8080/ExsJavaEEFormAuthWebApp/SignUp.jsp
* http://127.0.0.1:8080/ExsJavaEEFormAuthWebApp/secure/SecurePage.html

## Things to notice
* Vendor specific security realm configuration for the Java EE server.  See ``ExsJavaEEFormAuthWebApp\WebContent\WEB-INF\jboss-web.xml``
* Notice the Hibernate filter which creates a Hibernate session transaction for each incoming request and commits the transaction if not error occurs.  This unit-of-work is know as a session-per-request.
* If you change the location of your hibernate.cfg.xml you will need to update the HibernateUtil class
* hibernate.cfg.xml is using the datasource created in ``ExsJavaEEFormAuthWebApp\Resources\jboss-add-datasource-module.cli``
* Passwords are encrypted using Jasypt **StringPasswordEncryptor**

### The custom login module
* class name is **com.gwn.exs.ba.jboss.auth.LoginModule** and is referenced in the security domain.  See ```ExsJavaEEFormAuthWebApp\Resources\jboss-add-security-domain.cli```
* The security domain uses a custom login module which extends jboss class **AbstractServerLoginModule**
* Should be placed in 1 of the following locations:
  * ``{wildfly_home}\standalone\deployments\ExsJavaEEFormAuthWebApp.war\WEB-INF\classes``
  * or as a jar file in ``{wildfly_home}\standalone\deployments\ExsJavaEEFormAuthWebApp.war\WEB-INF\lib``
* A custom Principal is used called **UserPrincipal**.  This custom principal includes the user id, username, and role.  The role in this custom principal corresponds to the <security-role> in the deployment descriptor.
* Notice the method **#getRoleSets**, the role in my custom principal gets added to the caller principal group