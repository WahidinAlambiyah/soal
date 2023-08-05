-- DROP SCHEMA soal;

CREATE SCHEMA soal AUTHORIZATION postgres;

-- DROP SEQUENCE soal.sequence_generator;

CREATE SEQUENCE soal.sequence_generator
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1050
	CACHE 1
	NO CYCLE;-- soal.databasechangelog definition

-- Drop table

-- DROP TABLE soal.databasechangelog;

CREATE TABLE soal.databasechangelog (
	id varchar(255) NOT NULL,
	author varchar(255) NOT NULL,
	filename varchar(255) NOT NULL,
	dateexecuted timestamp NOT NULL,
	orderexecuted int4 NOT NULL,
	exectype varchar(10) NOT NULL,
	md5sum varchar(35) NULL,
	description varchar(255) NULL,
	"comments" varchar(255) NULL,
	tag varchar(255) NULL,
	liquibase varchar(20) NULL,
	contexts varchar(255) NULL,
	labels varchar(255) NULL,
	deployment_id varchar(10) NULL
);


-- soal.databasechangeloglock definition

-- Drop table

-- DROP TABLE soal.databasechangeloglock;

CREATE TABLE soal.databasechangeloglock (
	id int4 NOT NULL,
	"locked" bool NOT NULL,
	lockgranted timestamp NULL,
	lockedby varchar(255) NULL,
	CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id)
);


-- soal.jhi_authority definition

-- Drop table

-- DROP TABLE soal.jhi_authority;

CREATE TABLE soal.jhi_authority (
	"name" varchar(50) NOT NULL,
	CONSTRAINT jhi_authority_pkey PRIMARY KEY (name)
);


-- soal.jhi_user definition

-- Drop table

-- DROP TABLE soal.jhi_user;

CREATE TABLE soal.jhi_user (
	id int8 NOT NULL,
	login varchar(50) NOT NULL,
	password_hash varchar(60) NOT NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	email varchar(191) NULL,
	image_url varchar(256) NULL,
	activated bool NOT NULL,
	lang_key varchar(10) NULL,
	activation_key varchar(20) NULL,
	reset_key varchar(20) NULL,
	created_by varchar(50) NOT NULL,
	created_date timestamp NULL,
	reset_date timestamp NULL,
	last_modified_by varchar(50) NULL,
	last_modified_date timestamp NULL,
	CONSTRAINT jhi_user_pkey PRIMARY KEY (id),
	CONSTRAINT ux_user_email UNIQUE (email),
	CONSTRAINT ux_user_login UNIQUE (login)
);


-- soal.soalxsis definition

-- Drop table

-- DROP TABLE soal.soalxsis;

CREATE TABLE soal.soalxsis (
	id int8 NOT NULL,
	title varchar(255) NOT NULL,
	description varchar NULL,
	rating float4 NULL,
	image varchar(255) NULL,
	created_at date NULL,
	updated_at date NULL,
	CONSTRAINT soalxsis_pkey PRIMARY KEY (id)
);


-- soal.jhi_user_authority definition

-- Drop table

-- DROP TABLE soal.jhi_user_authority;

CREATE TABLE soal.jhi_user_authority (
	user_id int8 NOT NULL,
	authority_name varchar(50) NOT NULL,
	CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name),
	CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES soal.jhi_authority("name"),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES soal.jhi_user(id)
);
