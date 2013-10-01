# REST Clojure Proof of Concept #
This software is a proof of concept for a REST/Backbone stack for web development

Licensed under Apache 2.0 License

## Application database
The application it's prepared to use a database defined in "config.clj" default configuration

```clojure
(def database-spec {
    :classname "org.postgresql.Driver"
    :subprotocol "postgresql"
    :user "tasks"
    :subname "//localhost:5432/tasks"})
```

The application it's executed inside Leiningen.

In order to load the database schema you should execute the database migration:

```
lein migrate
```

If you don't want to use incremental migration but you wanna to restore the whole database you
should execute:

```
lein regenerate
```

## Application start
Once the database is ready you can start the server by executing:

```
lein ring server-headless
```

To check the application enter http://localhost:3000/login and login with admin/admin.

## Stack description
### Liberator
Library to use REST resourcers inside the Ring middleware

### Korma
A domain specific language for Clojure SQL access.

### Lobos
Library to maintain database schemas an generate database migrations.
Lobos contains a bug that doesn't allow migrations with sqlite3. We use Postgres database instead.

Work in progress...
