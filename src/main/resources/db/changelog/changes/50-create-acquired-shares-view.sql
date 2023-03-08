--liquibase formatted sql

--changeset digid0c:create_acquired_shares_view logicalFilePath:db/changelog/changes/50
DROP VIEW IF EXISTS acquired_shares_view CASCADE;

CREATE OR REPLACE VIEW acquired_shares_view AS
    SELECT gen_random_uuid() AS id, share_id, company_name, name, isin_code, country_code, activity_field, volume,
           reg_date, price_eur, round(price_eur * volume::numeric, 2) as total_price, employee_id,
           date_part('month', reg_date)::integer as month, date_part('year', reg_date)::integer as year
    FROM dev.stock
        INNER JOIN dev.share
            ON stock.share_id = share.id;
--rollback drop view acquired_shares_view cascade;