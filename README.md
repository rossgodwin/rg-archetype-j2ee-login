

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
* Create new database schema.  For this project call it 'sandbox'
* Create user table
```
CREATE TABLE `user` (`id` int(11) NOT NULL AUTO_INCREMENT, `username` varchar(45) NOT NULL, `password` longtext NOT NULL, `role` varchar(45) DEFAULT NULL, `email` varchar(100) NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `username_UNIQUE` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
```

### Java EE Server - WildFly
Download and extract WildFly to {wildfly_home}

### Eclipse setup server runtime
* Go to Window -> Preferences -> Server -> Runtime Environments
* Select Add...
* Choose **WildFly 10.0 (Experimental)**
* Server name: **WildFly 10.0 Sandbox**
* Create new runtime (next page)
* Name: **WildFly 10.0 Sandbox**
* Home Directory: browse to {wildfly_home} location and select
* Alternate JRE: select jdk1.8.0_77

### Import project from Git repository and configure project
* Right click on ExsJavaEEFormAuthWebApp -> Build Path -> Configure Build Path...
* Select Add Library...
* Select Server Runtime
* Select **WildFly 10.0 Sandbox**

### Eclipse setup server
* Window -> Show View -> Servers
* New -> Server
* Choose **WildFly 10.0 (Experimental)**
* Choose runtime server **WildFly 10.0 Sandbox**
* Start the server
* open a command prompt and navigate to {wildfly_home}\bin
* run add-user.bat to setup a Management user
* Access management console http://127.0.0.1:9990
* Notice the user/password was added in {wildfly_home}\standalone\configuration\mgmt-users.properties

### Configure WildFly
* Create datasource
 * open command prompt
 * navigate to {wildfly_home}\bin and run the following...
```
jboss-cli.bat --file=ExsJavaEEFormAuthWebApp\Resources\jboss-add-datasource-module.cli
```
  * creates mysql jdbc module in {wildfly_home}\modules\com\mysql\jdbc
  * creates datasource {wildfly_home}\standalone\configuration\standalone.xml
  * also in the admin console -> Configuration -> Start -> Subsystems -> Datasources -> Non-XA - you should see MySqlSandboxDS
* Create security domain
 * open command prompt
 * navigate to {wildfly_home}\bin and run the following...
```
jboss-cli.bat --file=ExsJavaEEFormAuthWebApp\Resources\jboss-add-security-domain.cli
```

### In Eclipses servers view
* Right-click **WildFly 10.0 Sandbox** -> Add and Remove...
* Add **ExsJavaEEFormAuthWebApp** project

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

# Hibernate
The following are some quotes and notes from taken from Hibernate 5.0 documentation.

<!-- -->
> A SessionFactory is an expensive-to-create, threadsafe object, intended to be shared by all application threads. It is created once, usually on application startup, from a Configuration instance.

<!-- -->
> A Session is an inexpensive, non-threadsafe object that should be used once and then discarded for: a single request, a conversation or a single unit of work. A Session will not obtain a JDBC Connection, or a Datasource, unless it is needed. It will not consume any resources until used.

<!-- -->
> Do not use the session-per-operation antipattern: do not open and close a Session for every simple database call in a single thread.

<!-- -->
> The most common pattern in a multi-user client/server application is session-per-request.

<!-- -->
> In this model, a request from the client is sent to the server, where the Hibernate persistence layer runs. A new Hibernate Session is opened, and all database operations are executed in this unit of work. On completion of the work, and once the response for the client has been prepared, the session is flushed and closed.

<!-- -->
> Starting with version 3.0.1, Hibernate added the SessionFactory.getCurrentSession() method. Initially, this assumed usage of JTA transactions, where the JTA transaction defined both the scope and context of a current session. Given the maturity of the numerous stand-alone JTA TransactionManager implementations, most, if not all, applications should be using JTA transaction management, whether or not they are deployed into a J2EE container. Based on that, the JTA-based contextual sessions are all you need to use.

* Transaction
 * unit of work, if onen step fails the whole unit of work must fail (atomicity all operations are executed as an atomic unit)
 * keeps data clean and in consistant state by keeping it hidden from other transactions
 * sql statements execute inside a transaction

* Programmatic transaction demarcation
 * you begin the transaction and commit or rollback
 * transaction interfaces
 * java.sql.Connection - direct usage of JDBC transactions in hibernate is discouraged b/c it binds your application to JDBC environment
 * org.hibernate.Transaction - works in nonmanaged plain JDBC environment and in an application server with JTA (Java Transaction API) as system transaction service
 * javax.transaction.UserTransaction - should be primary choice whenever you have JTA-compatible transaction service
 * javax.persistence.EntityTransaction - interface for programmatic transaction control in Java SE applications that use Java Persistence

* Declarative transaction demarcation
 * you declare when you want to work inside a transaction, then the runtime environment handles this concern
 * EJB container privdes declarative transaction services in Java; called container-managed transactions (CMT)

* 10.1.2 on
 * Programmatic transactions in Java SE
  * A hibernate session is lazy, so it won't consume any resources unless they're needed.  A JDBC connection is obtained only when a transaction begins.
  * beginTransaction() equivalent to setAutoCommit(false)
  * NOTE: starting in Hibernate 3 all exceptions thrown are subtypes of the unchecked RuntimeException, before all exceptions thrown by Hibernate were checked
   *All Hibernate exceptions are fatal
