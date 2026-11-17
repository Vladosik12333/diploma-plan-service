--liquibase formatted sql

--changeset diploma-plan-service:4-normalize-places
ALTER TABLE plan_variants
    ADD CONSTRAINT ux_plan_variants_type UNIQUE (generated_plan_id, type);


ALTER TABLE plan_items
    DROP COLUMN latitude,
    DROP COLUMN longitude,
    DROP COLUMN address,
    DROP COLUMN open_time,
    DROP COLUMN close_time,
    ADD COLUMN importance INT,
    ADD CONSTRAINT ux_plan_items_position UNIQUE (plan_variant_id, day_date, position);
