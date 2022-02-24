-- for my own experiments
ALTER TABLE bookdb.author_jpa
    ADD COLUMN profile_picture varchar(25) AFTER last_name;