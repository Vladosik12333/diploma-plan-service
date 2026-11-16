--liquibase formatted sql

--changeset diploma-plan-service:2-update-plan-items
ALTER TABLE plan_items
    DROP COLUMN period,
    ADD COLUMN latitude       NUMERIC(9, 6),
    ADD COLUMN longitude      NUMERIC(9, 6),
    ADD COLUMN address        VARCHAR(500),
    ADD COLUMN start_time     TIME,
    ADD COLUMN end_time       TIME,
    ADD COLUMN estimated_cost INT,
    ADD COLUMN open_time      TIME,
    ADD COLUMN close_time     TIME;
