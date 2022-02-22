-- for my own experiments
ALTER TABLE bookdb.book_hibernate
    ADD COLUMN price varchar(15) AFTER author_id;