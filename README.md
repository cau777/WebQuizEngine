# WebQuizEngine

A Spring REST API to create and solve quizzes. It includes a simple registration process and uses authentication to keep
track of users' completed quizzes.

## Allowed methods

- GET /api/quizzes?page=0 => returns a page with 10 quizzes
- GET /api/quizzes/{id} => returns a specific quiz or 404
- POST /api/quizzes => creates a new question. The request body should contain a title, a text, an array of options and
  an array of correct options
- POST /api/quizzes/{id}/solve => Tries to solve a question. The request body should contain an array of correct
  options. It returns a JSON object specifying the success.
- DELETE /api/quizzes/{id} => Deletes a question. Only the author of a question can delete it.
- GET /api/quizzes/completed?page=0 => returns a page with 10 completed questions by the current user
- POST /api/register => creates a new user. The request body should contain an email and a password