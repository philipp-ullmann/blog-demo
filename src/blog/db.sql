-- name: create-comments-table!
CREATE TABLE IF NOT EXISTS comments (
  id      INT(11)      NOT NULL AUTO_INCREMENT,
  title   VARCHAR(255) NOT NULL,
  content TEXT         NOT NULL,
	video   VARCHAR(255) NULL,

  PRIMARY KEY (id)
)

-- name: drop-comments-table!
DROP TABLE IF EXISTS comments;

-- name: insert-comment<!
-- Inserts a new comment.
INSERT INTO comments (title, content, video)
VALUES (:title, :content, :video)

--name: all-comments
-- Finds all comments.
SELECT *
FROM comments
ORDER BY id DESC

--name: comment-by-id
-- Finds a comment by id.
SELECT *
FROM comments
WHERE id = :id
