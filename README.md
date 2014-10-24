# Blog demo

Blog demo server to recruit frontend programmers for the company **create.at**.

Create and edit application **configuration** file *.lein-env*:

    cp lein-env-example .lein-env

**Start server** with:

    lein run

Run **tests** with:

    lein midje

## API

**List all comments**

Request:

    GET => /comments

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

    GET => /comments/1

JSON Response:

```json
{"id":      1,
 "title":   "Ring",
 "content": "Clojure is a dynamic programming language that targets the Java Virtual Machine",
 "video":   "/smile.mp4"}
```
