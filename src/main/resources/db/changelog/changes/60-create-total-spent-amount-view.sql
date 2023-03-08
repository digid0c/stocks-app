--liquibase formatted sql

--changeset digid0c:create_total_spent_amount_view logicalFilePath:db/changelog/changes/60
DROP VIEW IF EXISTS total_spent_amount_view CASCADE;

CREATE OR REPLACE VIEW total_spent_amount_view AS
    SELECT gen_random_uuid() AS id, employee_id, month, year, SUM(total_price) as total_spent_amount
    FROM dev.acquired_shares_view
    GROUP BY employee_id, month, year;
--rollback drop view total_spent_amount_view cascade;