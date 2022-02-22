-- for my own experiments
ALTER TABLE bookdb.author_hibernate
    ADD COLUMN some_mutable_field varchar(15) AFTER last_name;