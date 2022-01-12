CREATE SCHEMA challenge AUTHORIZATION admin;

CREATE ROLE tenpo WITH LOGIN PASSWORD 'wg3WJh94';

GRANT USAGE ON SCHEMA challenge TO tenpo;

CREATE SEQUENCE challenge.app_user_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE challenge.execution_history_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE challenge.execution_history (
	id int4 NOT NULL DEFAULT nextval('challenge.execution_history_seq'::regclass),
	name varchar(50) NOT NULL,
	execution_date timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	execution_time int4 NOT NULL DEFAULT 0,
	execution_result varchar(20) NOT NULL DEFAULT 'NA',
	CONSTRAINT pk_execution_history PRIMARY KEY (id)
);

COMMENT ON COLUMN challenge.execution_history.id IS 'Identificador único de ejecución.';
COMMENT ON COLUMN challenge.execution_history.name IS 'Nombre informativo de ejecución.';
COMMENT ON COLUMN challenge.execution_history.execution_date IS 'Fecha de ejecución.';
COMMENT ON COLUMN challenge.execution_history.execution_time IS 'Tiempo de ejecución en milisegundos.';
COMMENT ON COLUMN challenge.execution_history.execution_result IS 'Resultado de ejecución. SUCCESS, ERROR, NOT AVAILABLE(NA)';

CREATE TABLE challenge.app_user (
	id int4 NOT NULL DEFAULT nextval('challenge.app_user_seq'::regclass),
	username varchar(50) NOT NULL,
	password varchar(250) NOT NULL,
	name varchar(50) NOT NULL,
	surname varchar(50) NULL,
	email varchar(100) NOT NULL,
	CONSTRAINT pk_app_user PRIMARY KEY (id)
);

COMMENT ON COLUMN challenge.app_user.id IS 'Identificador único de usuario.';
COMMENT ON COLUMN challenge.app_user.username IS 'Nombre de usuario o alias.';
COMMENT ON COLUMN challenge.app_user.password IS 'Contraseña de usuario.';
COMMENT ON COLUMN challenge.app_user.name IS 'Nombre real de usuario.';
COMMENT ON COLUMN challenge.app_user.surname IS 'Apellido real de usuario.';
COMMENT ON COLUMN challenge.app_user.email IS 'Correo electrónico de usuario.';

ALTER SEQUENCE challenge.app_user_seq OWNED BY challenge.app_user.id;

ALTER SEQUENCE challenge.execution_history_seq OWNED BY challenge.execution_history.id;

GRANT USAGE ON SEQUENCE challenge.app_user_seq TO tenpo;

GRANT USAGE ON SEQUENCE challenge.execution_history_seq TO tenpo;

ALTER TABLE challenge.execution_history OWNER TO admin;

ALTER TABLE challenge.app_user OWNER TO admin;

GRANT ALL ON TABLE challenge.execution_history TO tenpo;

GRANT ALL ON TABLE challenge.app_user TO tenpo;

