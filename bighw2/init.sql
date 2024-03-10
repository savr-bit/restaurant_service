BEGIN;


CREATE TABLE IF NOT EXISTS public.food_order
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    total_amount integer NOT NULL,
    user_id integer NOT NULL,
    status character varying(256) COLLATE pg_catalog."default" NOT NULL,
    position_number integer NOT NULL,
    CONSTRAINT food_order_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.menu_item
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    price integer NOT NULL,
    timetocook integer NOT NULL,
    item_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    quantity integer NOT NULL,
    CONSTRAINT menu_item_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.order_menu_item
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    menu_item_id integer,
    order_id integer NOT NULL,
    status character varying COLLATE pg_catalog."default",
    CONSTRAINT order_menu_item_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.person
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    login character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    passwordhash character varying(255) COLLATE pg_catalog."default",
    usertype character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.food_order
    ADD CONSTRAINT user_id FOREIGN KEY (user_id)
    REFERENCES public.person (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.order_menu_item
    ADD CONSTRAINT fknir5l0bpyuugcfvp11t08r893 FOREIGN KEY (order_id)
    REFERENCES public.food_order (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.order_menu_item
    ADD CONSTRAINT fkoudm2juuy5n0gojkem824fy2k FOREIGN KEY (menu_item_id)
    REFERENCES public.menu_item (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

insert into person(login, name, passwordHash, usertype) values ('root','Администратор','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2','admin');

END;