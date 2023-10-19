CREATE TABLE comment (
  id BIGINT NOT NULL,
   status SMALLINT,
   user_name VARCHAR(255) NOT NULL,
   text VARCHAR(255) NOT NULL,
   created_at date NOT NULL,
   updated_at date NOT NULL,
   art_id BIGINT,
   CONSTRAINT pk_comment PRIMARY KEY (id)
);

ALTER TABLE comment ADD CONSTRAINT FK_COMMENT_ON_ART FOREIGN KEY (art_id) REFERENCES art (id);