PGDMP                  	    |            todo_db    17.0    17.0                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false                       1262    25152    todo_db    DATABASE     z   CREATE DATABASE todo_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Polish_Poland.1250';
    DROP DATABASE todo_db;
                     postgres    false            �            1259    25153    items    TABLE     �   CREATE TABLE public.items (
    id integer NOT NULL,
    completed boolean,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(255),
    title character varying(255),
    list_id integer NOT NULL
);
    DROP TABLE public.items;
       public         heap r       postgres    false            �            1259    25177 	   items_seq    SEQUENCE     s   CREATE SEQUENCE public.items_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.items_seq;
       public               postgres    false            �            1259    25160    lists    TABLE     �   CREATE TABLE public.lists (
    id integer NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(255),
    title character varying(255),
    user_id integer NOT NULL
);
    DROP TABLE public.lists;
       public         heap r       postgres    false            �            1259    25178 	   lists_seq    SEQUENCE     s   CREATE SEQUENCE public.lists_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.lists_seq;
       public               postgres    false            �            1259    25167    registered_user_authorities    TABLE     �   CREATE TABLE public.registered_user_authorities (
    registered_user_id integer NOT NULL,
    authorities character varying(255)
);
 /   DROP TABLE public.registered_user_authorities;
       public         heap r       postgres    false            �            1259    25170    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    email character varying(255) NOT NULL,
    fullname character varying(255) NOT NULL,
    password character varying(255) NOT NULL
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    25179 	   users_seq    SEQUENCE     s   CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.users_seq;
       public               postgres    false            �          0    25153    items 
   TABLE DATA           W   COPY public.items (id, completed, created_at, description, title, list_id) FROM stdin;
    public               postgres    false    217   �                  0    25160    lists 
   TABLE DATA           L   COPY public.lists (id, created_at, description, title, user_id) FROM stdin;
    public               postgres    false    218   o                 0    25167    registered_user_authorities 
   TABLE DATA           V   COPY public.registered_user_authorities (registered_user_id, authorities) FROM stdin;
    public               postgres    false    219   �                 0    25170    users 
   TABLE DATA           >   COPY public.users (id, email, fullname, password) FROM stdin;
    public               postgres    false    220                     0    0 	   items_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.items_seq', 101, true);
          public               postgres    false    221                       0    0 	   lists_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.lists_seq', 51, true);
          public               postgres    false    222                       0    0 	   users_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.users_seq', 51, true);
          public               postgres    false    223            f           2606    25159    items items_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.items DROP CONSTRAINT items_pkey;
       public                 postgres    false    217            h           2606    25166    lists lists_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.lists DROP CONSTRAINT lists_pkey;
       public                 postgres    false    218            j           2606    25176    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    220            m           2606    25190 7   registered_user_authorities fk37inp7v45p2m2cj97mtf4ajw5    FK CONSTRAINT     �   ALTER TABLE ONLY public.registered_user_authorities
    ADD CONSTRAINT fk37inp7v45p2m2cj97mtf4ajw5 FOREIGN KEY (registered_user_id) REFERENCES public.users(id);
 a   ALTER TABLE ONLY public.registered_user_authorities DROP CONSTRAINT fk37inp7v45p2m2cj97mtf4ajw5;
       public               postgres    false    219    4714    220            l           2606    25185 !   lists fke59kv852m4k3g8kmefph4i3kx    FK CONSTRAINT     �   ALTER TABLE ONLY public.lists
    ADD CONSTRAINT fke59kv852m4k3g8kmefph4i3kx FOREIGN KEY (user_id) REFERENCES public.users(id);
 K   ALTER TABLE ONLY public.lists DROP CONSTRAINT fke59kv852m4k3g8kmefph4i3kx;
       public               postgres    false    4714    220    218            k           2606    25180 !   items fkrqa704hx19ty2bxvb1w4efous    FK CONSTRAINT     �   ALTER TABLE ONLY public.items
    ADD CONSTRAINT fkrqa704hx19ty2bxvb1w4efous FOREIGN KEY (list_id) REFERENCES public.lists(id);
 K   ALTER TABLE ONLY public.items DROP CONSTRAINT fkrqa704hx19ty2bxvb1w4efous;
       public               postgres    false    218    4712    217            �   �   x��νn� ���p\��8��͵�-��S�R%���M��������: ?����RXݳQI�.�0���'��"�N�z���k�+ A���0Gf�?�Oha�0=(n4L�v��{q�]�޲��/p�ߡD˿����%�.�T��iY�=t�-D�\�ݯ�����#�9"-�L!�=�װ�nN�����mWF�~sR�          l   x�]�;
�0 �zr��23*�v.l����4!	x}�}�#`�uE���-����]#01�2����<~��3��Wj��IZ���kHXv��Z�bt��ĿVJ�G�&            x�3���q�v�2Bb��qqq m��         �   x�U̹�0  Й~�s�6�B��ƥr�@�iA���8�����IT�J��ܖ�q�}T�5"#U>���M��)��j�F�FL�gE�vL����S���J���[�}g�gN�b`�_�Ľ;�q�^B��φ����̢Ԍt���f;E��<���G� g7 x�:�     