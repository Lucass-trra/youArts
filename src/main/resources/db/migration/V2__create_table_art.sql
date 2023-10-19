CREATE TABLE art (
  id BIGINT NOT NULL,
   status SMALLINT,
   title VARCHAR(255) NOT NULL,
   description VARCHAR(255) NOT NULL,
   image VARCHAR(255) NOT NULL,
   created_at date NOT NULL,
   update_at date NOT NULL,
   user_id BIGINT,
   CONSTRAINT pk_art PRIMARY KEY (id)
);

ALTER TABLE art ADD CONSTRAINT FK_ART_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);