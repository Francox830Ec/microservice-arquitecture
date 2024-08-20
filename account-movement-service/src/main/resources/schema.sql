CREATE SCHEMA if not exists "transaction";

CREATE TABLE if not exists transaction.account (
    acc_id uuid NOT NULL,
    cli_id uuid NULL,
    acc_initial_balance numeric(38, 2) NULL,
    acc_number varchar(255) NULL,
    acc_state bool NULL,
    acc_type varchar(255) NULL,
    CONSTRAINT account_pkey PRIMARY KEY (acc_id)
);

CREATE TABLE if not exists transaction.movement (
     mov_id uuid NOT NULL,
     acc_id uuid NULL,
     mov_date timestamp NULL,
     mov_type varchar(255) NULL,
     mov_value numeric(38, 2) NULL,
     mov_final_balance numeric(38, 2) NULL,
     CONSTRAINT movement_pkey PRIMARY KEY (mov_id),
     CONSTRAINT account_fkey FOREIGN KEY (acc_id) REFERENCES transaction.account(acc_id)
);