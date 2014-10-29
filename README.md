# Blog demo

This is a simple blog demo server, which provides an API to fetch comments.

*Definition of task:*

Please develop a rich javascript client, which lists all available comments. If an user clicks on a comment the full comment description should be visible and the corresponding video should be started. The client should run in all current browser, tablets and mobile phones.

## API

**List all comments**

Request:

    GET => https://blog-demo-create.herokuapp.com/comments

JSON Response:

```json
[
  {"id":      2,
   "title":   "Clojure",
   "content": "Clojure is a dynamic programming language..."},

  {"id":      1,
   "title":   "Ring",
   "content": "Ring is a Clojure web applications library..."}
]
```

**Show details of a comment**

Request:

    GET => https://blog-demo-create.herokuapp.com/comments/1

JSON Response:

```json
{"id":      1,
 "title":   "Ring",
 "content": "Clojure is a dynamic programming language that targets the Java Virtual Machine",
 "video":   "/smile.mp4"}
```

## Server development

Create and edit application **configuration** file *.lein-env*:

    cp lein-env-example .lein-env

**Start server** with:

    lein run

Run **tests** with:

    lein midje

## Production server

The production server run's on [heroku](http://heroku.com).

Web url:

    https://blog-demo-create.herokuapp.com

Deploy with:

    git push heroku master

View logs with:

    heroku logs --tail
