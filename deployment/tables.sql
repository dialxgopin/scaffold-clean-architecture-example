CREATE TABLE IF NOT EXISTS public.account (
	product_id serial4 NOT NULL,
	account_holder_fisrtname varchar NULL,
	account_holder_lastname varchar NULL,
	CONSTRAINT account_pk PRIMARY KEY (product_id)
);

CREATE TABLE public.card (
	id serial4 NOT NULL,
	card_id varchar NULL,
	account_id int4 NULL,
	valid_thru date NULL,
	currency varchar NULL,
	balance numeric NULL DEFAULT 0.0,
	active bool NULL DEFAULT false,
	CONSTRAINT card_pk PRIMARY KEY (id),
	CONSTRAINT card_fk FOREIGN KEY (account_id) REFERENCES public.account(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);