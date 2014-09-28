-- MySQL interprets datetime literals with respect to the current time
-- zone, but stores TIMESTAMP values as UTC times. If the current time
-- zone is not UTC, then TIMESTAMP values are first converted before
-- storage. This may make one of the values we test,
-- 1970-01-01 00:00:01, illegal. So we set the current time zone to UTC.
SET time_zone='+00:00';

-- The following types are available when creating tables, but are immediately
-- converted to a MySQL type, and hence not tested here.
--
-- Other Type           | MySQL Type
-- ---------------------+-------------
-- BOOL                 | TINYINT
-- BOOLEAN              | TINYINT
-- CHARACTER VARYING(M) | VARCHAR(M)
-- FIXED                | DECIMAL
-- FLOAT4               | FLOAT
-- FLOAT8               | DOUBLE
-- INT1                 | TINYINT
-- INT2                 | SMALLINT
-- INT3                 | MEDIUMINT
-- INT4                 | INT
-- INT8                 | BIGINT
-- LONG VARBINARY       | MEDIUMBLOB
-- LONG VARCHAR         | MEDIUMTEXT
-- LONG                 | MEDIUMTEXT
-- MIDDLEINT            | MEDIUMINT
-- NUMERIC              | DECIMAL

DROP TABLE IF EXISTS T_SERIAL;
CREATE TABLE T_SERIAL (ID INT PRIMARY KEY, VALUE SERIAL);
INSERT INTO T_SERIAL VALUES (1, 1);
INSERT INTO T_SERIAL VALUES (2, 2);
INSERT INTO T_SERIAL VALUES (3, 18446744073709551615);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_BIT_4;
CREATE TABLE T_BIT_4 (ID INT PRIMARY KEY, VALUE BIT(4) NULL);
INSERT INTO T_BIT_4 VALUES (0, NULL);
INSERT INTO T_BIT_4 VALUES (1, B'0000');
INSERT INTO T_BIT_4 VALUES (2, B'0001');
INSERT INTO T_BIT_4 VALUES (3, B'1000');
INSERT INTO T_BIT_4 VALUES (4, B'1111');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_BIT;
CREATE TABLE T_BIT (ID INT PRIMARY KEY, VALUE BIT NULL);
INSERT INTO T_BIT VALUES (0, NULL);
INSERT INTO T_BIT VALUES (1, B'0');
INSERT INTO T_BIT VALUES (2, B'1');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_TINYINT;
CREATE TABLE T_TINYINT (ID INT PRIMARY KEY, VALUE TINYINT NULL);
INSERT INTO T_TINYINT VALUES (0, NULL);
INSERT INTO T_TINYINT VALUES (1, 0);
INSERT INTO T_TINYINT VALUES (2, 1);
INSERT INTO T_TINYINT VALUES (3, -128);
INSERT INTO T_TINYINT VALUES (4, 127);

DROP TABLE IF EXISTS T_TINYINT_1;
CREATE TABLE T_TINYINT_1 (ID INT PRIMARY KEY, VALUE TINYINT(1) NULL);
INSERT INTO T_TINYINT_1 VALUES (0, NULL);
INSERT INTO T_TINYINT_1 VALUES (1, 0);
INSERT INTO T_TINYINT_1 VALUES (2, 1);
INSERT INTO T_TINYINT_1 VALUES (3, 100);

DROP TABLE IF EXISTS T_TINYINT_UNSIGNED;
CREATE TABLE T_TINYINT_UNSIGNED (ID INT PRIMARY KEY, VALUE TINYINT UNSIGNED NULL);
INSERT INTO T_TINYINT_UNSIGNED VALUES (0, NULL);
INSERT INTO T_TINYINT_UNSIGNED VALUES (1, 0);
INSERT INTO T_TINYINT_UNSIGNED VALUES (2, 1);
INSERT INTO T_TINYINT_UNSIGNED VALUES (3, 255);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_SMALLINT;
CREATE TABLE T_SMALLINT (ID INT PRIMARY KEY, VALUE SMALLINT NULL);
INSERT INTO T_SMALLINT VALUES (0, NULL);
INSERT INTO T_SMALLINT VALUES (1, 0);
INSERT INTO T_SMALLINT VALUES (2, 1);
INSERT INTO T_SMALLINT VALUES (3, -32768);
INSERT INTO T_SMALLINT VALUES (4, 32767);

DROP TABLE IF EXISTS T_SMALLINT_UNSIGNED;
CREATE TABLE T_SMALLINT_UNSIGNED (ID INT PRIMARY KEY, VALUE SMALLINT UNSIGNED NULL);
INSERT INTO T_SMALLINT_UNSIGNED VALUES (0, NULL);
INSERT INTO T_SMALLINT_UNSIGNED VALUES (1, 0);
INSERT INTO T_SMALLINT_UNSIGNED VALUES (2, 1);
INSERT INTO T_SMALLINT_UNSIGNED VALUES (3, 65535);

DROP TABLE IF EXISTS T_MEDIUMINT;
CREATE TABLE T_MEDIUMINT (ID INT PRIMARY KEY, VALUE MEDIUMINT NULL);
INSERT INTO T_MEDIUMINT VALUES (0, NULL);
INSERT INTO T_MEDIUMINT VALUES (1, 0);
INSERT INTO T_MEDIUMINT VALUES (2, 1);
INSERT INTO T_MEDIUMINT VALUES (3, -8388608);
INSERT INTO T_MEDIUMINT VALUES (4, 8388607);

DROP TABLE IF EXISTS T_MEDIUMINT_UNSIGNED;
CREATE TABLE T_MEDIUMINT_UNSIGNED (ID INT PRIMARY KEY, VALUE MEDIUMINT UNSIGNED NULL);
INSERT INTO T_MEDIUMINT_UNSIGNED VALUES (0, NULL);
INSERT INTO T_MEDIUMINT_UNSIGNED VALUES (1, 0);
INSERT INTO T_MEDIUMINT_UNSIGNED VALUES (2, 1);
INSERT INTO T_MEDIUMINT_UNSIGNED VALUES (3, 16777215);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_INT;
CREATE TABLE T_INT (ID INT PRIMARY KEY, VALUE INT NULL);
INSERT INTO T_INT VALUES (0, NULL);
INSERT INTO T_INT VALUES (1, 0);
INSERT INTO T_INT VALUES (2, 1);
INSERT INTO T_INT VALUES (3, -2147483648);
INSERT INTO T_INT VALUES (4, 2147483647);

DROP TABLE IF EXISTS T_INT_UNSIGNED;
CREATE TABLE T_INT_UNSIGNED (ID INT PRIMARY KEY, VALUE INT UNSIGNED NULL);
INSERT INTO T_INT_UNSIGNED VALUES (0, NULL);
INSERT INTO T_INT_UNSIGNED VALUES (1, 0);
INSERT INTO T_INT_UNSIGNED VALUES (2, 1);
INSERT INTO T_INT_UNSIGNED VALUES (3, 4294967295);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_INTEGER;
CREATE TABLE T_INTEGER (ID INT PRIMARY KEY, VALUE INTEGER NULL);
INSERT INTO T_INTEGER VALUES (0, NULL);
INSERT INTO T_INTEGER VALUES (1, 0);
INSERT INTO T_INTEGER VALUES (2, 1);
INSERT INTO T_INTEGER VALUES (3, -2147483648);
INSERT INTO T_INTEGER VALUES (4, 2147483647);

DROP TABLE IF EXISTS T_INTEGER_UNSIGNED;
CREATE TABLE T_INTEGER_UNSIGNED (ID INT PRIMARY KEY, VALUE INTEGER UNSIGNED NULL);
INSERT INTO T_INTEGER_UNSIGNED VALUES (0, NULL);
INSERT INTO T_INTEGER_UNSIGNED VALUES (1, 0);
INSERT INTO T_INTEGER_UNSIGNED VALUES (2, 1);
INSERT INTO T_INTEGER_UNSIGNED VALUES (3, 4294967295);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_BIGINT;
CREATE TABLE T_BIGINT (ID INT PRIMARY KEY, VALUE BIGINT NULL);
INSERT INTO T_BIGINT VALUES (0, NULL);
INSERT INTO T_BIGINT VALUES (1, 0);
INSERT INTO T_BIGINT VALUES (2, 1);
INSERT INTO T_BIGINT VALUES (3, -9223372036854775808);
INSERT INTO T_BIGINT VALUES (4, 9223372036854775807);

DROP TABLE IF EXISTS T_BIGINT_UNSIGNED;
CREATE TABLE T_BIGINT_UNSIGNED (ID INT PRIMARY KEY, VALUE BIGINT UNSIGNED NULL);
INSERT INTO T_BIGINT_UNSIGNED VALUES (0, NULL);
INSERT INTO T_BIGINT_UNSIGNED VALUES (1, 0);
INSERT INTO T_BIGINT_UNSIGNED VALUES (2, 1);
INSERT INTO T_BIGINT_UNSIGNED VALUES (3, 18446744073709551615);

DROP TABLE IF EXISTS T_DECIMAL;
CREATE TABLE T_DECIMAL (ID INT PRIMARY KEY, VALUE DECIMAL NULL);
INSERT INTO T_DECIMAL VALUES (0, NULL);
INSERT INTO T_DECIMAL VALUES (1, 0);
INSERT INTO T_DECIMAL VALUES (2, 1);
INSERT INTO T_DECIMAL VALUES (3, 100000000);
INSERT INTO T_DECIMAL VALUES (4, -100000000);

-- same as for HSQLDB
DROP TABLE IF EXISTS T_DECIMAL_4_2;
CREATE TABLE T_DECIMAL_4_2 (ID INT PRIMARY KEY, VALUE DECIMAL(4,2) NULL);
INSERT INTO T_DECIMAL_4_2 VALUES (0, NULL);
INSERT INTO T_DECIMAL_4_2 VALUES (1, 0);
INSERT INTO T_DECIMAL_4_2 VALUES (2, 1.00);
INSERT INTO T_DECIMAL_4_2 VALUES (3, 4.95);
INSERT INTO T_DECIMAL_4_2 VALUES (4, 99.99);
INSERT INTO T_DECIMAL_4_2 VALUES (5, -99.99);

DROP TABLE IF EXISTS T_DEC;
CREATE TABLE T_DEC (ID INT PRIMARY KEY, VALUE DEC NULL);
INSERT INTO T_DEC VALUES (0, NULL);
INSERT INTO T_DEC VALUES (1, 0);
INSERT INTO T_DEC VALUES (2, 1);
INSERT INTO T_DEC VALUES (3, 100000000);
INSERT INTO T_DEC VALUES (4, -100000000);

DROP TABLE IF EXISTS T_DEC_4_2;
CREATE TABLE T_DEC_4_2 (ID INT PRIMARY KEY, VALUE DEC(4,2) NULL);
INSERT INTO T_DEC_4_2 VALUES (0, NULL);
INSERT INTO T_DEC_4_2 VALUES (1, 0);
INSERT INTO T_DEC_4_2 VALUES (2, 1.00);
INSERT INTO T_DEC_4_2 VALUES (3, 4.95);
INSERT INTO T_DEC_4_2 VALUES (4, 99.99);
INSERT INTO T_DEC_4_2 VALUES (5, -99.99);

DROP TABLE IF EXISTS T_FLOAT;
CREATE TABLE T_FLOAT (ID INT PRIMARY KEY, VALUE FLOAT NULL);
INSERT INTO T_FLOAT VALUES (0, NULL);
INSERT INTO T_FLOAT VALUES (1, 0E0);
INSERT INTO T_FLOAT VALUES (2, 1E0);
INSERT INTO T_FLOAT VALUES (3, -1E0);
INSERT INTO T_FLOAT VALUES (4, -3E+38);
INSERT INTO T_FLOAT VALUES (5, -1E-38);
INSERT INTO T_FLOAT VALUES (6, 1E-38);
INSERT INTO T_FLOAT VALUES (7, 3E+38);

DROP TABLE IF EXISTS T_DOUBLE;
CREATE TABLE T_DOUBLE (ID INT PRIMARY KEY, VALUE DOUBLE NULL);
INSERT INTO T_DOUBLE VALUES (0, NULL);
INSERT INTO T_DOUBLE VALUES (1, 0E0);
INSERT INTO T_DOUBLE VALUES (2, 1E0);
INSERT INTO T_DOUBLE VALUES (3, -1E0);
INSERT INTO T_DOUBLE VALUES (4, -1E+308);
INSERT INTO T_DOUBLE VALUES (5, -2E-308);
INSERT INTO T_DOUBLE VALUES (6, 2E-308);
INSERT INTO T_DOUBLE VALUES (7, 1E+308);

DROP TABLE IF EXISTS T_REAL;
CREATE TABLE T_REAL (ID INT PRIMARY KEY, VALUE REAL NULL);
INSERT INTO T_REAL VALUES (0, NULL);
INSERT INTO T_REAL VALUES (1, 0E0);
INSERT INTO T_REAL VALUES (2, 1E0);
INSERT INTO T_REAL VALUES (3, -1E0);
INSERT INTO T_REAL VALUES (4, -1E+308);
INSERT INTO T_REAL VALUES (5, -2E-308);
INSERT INTO T_REAL VALUES (6, 2E-308);
INSERT INTO T_REAL VALUES (7, 1E+308);

DROP TABLE IF EXISTS T_DOUBLE_PRECISION;
CREATE TABLE T_DOUBLE_PRECISION (ID INT PRIMARY KEY, VALUE DOUBLE PRECISION NULL);
INSERT INTO T_DOUBLE_PRECISION VALUES (0, NULL);
INSERT INTO T_DOUBLE_PRECISION VALUES (1, 0E0);
INSERT INTO T_DOUBLE_PRECISION VALUES (2, 1E0);
INSERT INTO T_DOUBLE_PRECISION VALUES (3, -1E0);
INSERT INTO T_DOUBLE_PRECISION VALUES (4, -1E+308);
INSERT INTO T_DOUBLE_PRECISION VALUES (5, -2E-308);
INSERT INTO T_DOUBLE_PRECISION VALUES (6, 2E-308);
INSERT INTO T_DOUBLE_PRECISION VALUES (7, 1E+308);

DROP TABLE IF EXISTS T_DATE;
CREATE TABLE T_DATE (ID INT PRIMARY KEY, VALUE DATE NULL);
INSERT INTO T_DATE VALUES (0, NULL);
INSERT INTO T_DATE VALUES (1, DATE '1000-01-01');
INSERT INTO T_DATE VALUES (2, DATE '2012-03-07');
INSERT INTO T_DATE VALUES (3, DATE '9999-12-31');
INSERT INTO T_DATE VALUES (4, DATE '1978-11-30');
INSERT INTO T_DATE VALUES (5, DATE '1979-00-00');
INSERT INTO T_DATE VALUES (6, DATE '0000-00-00');

DROP TABLE IF EXISTS T_DATETIME;
CREATE TABLE T_DATETIME (ID INT PRIMARY KEY, VALUE DATETIME NULL);
INSERT INTO T_DATETIME VALUES (0, NULL);
INSERT INTO T_DATETIME VALUES (1, TIMESTAMP '1000-01-01 00:00:00');
INSERT INTO T_DATETIME VALUES (2, TIMESTAMP '2012-03-07 20:39:21');
INSERT INTO T_DATETIME VALUES (3, TIMESTAMP '9999-12-31 23:59:59');
INSERT INTO T_DATETIME VALUES (4, TIMESTAMP '1978-11-30 00:00:00');
INSERT INTO T_DATETIME VALUES (5, TIMESTAMP '1979-00-00 00:00:00');
INSERT INTO T_DATETIME VALUES (6, TIMESTAMP '0000-00-00 00:00:00');

DROP TABLE IF EXISTS T_TIMESTAMP;
CREATE TABLE T_TIMESTAMP (ID INT PRIMARY KEY, VALUE TIMESTAMP NULL);
INSERT INTO T_TIMESTAMP VALUES (0, NULL);
INSERT INTO T_TIMESTAMP VALUES (1, TIMESTAMP '1970-01-01 00:00:01');
INSERT INTO T_TIMESTAMP VALUES (2, TIMESTAMP '2012-03-07 20:39:21');
INSERT INTO T_TIMESTAMP VALUES (3, TIMESTAMP '2038-01-19 03:14:07');
INSERT INTO T_TIMESTAMP VALUES (4, TIMESTAMP '0000-00-00 00:00:00');

DROP TABLE IF EXISTS T_TIME;
CREATE TABLE T_TIME (ID INT PRIMARY KEY, VALUE TIME NULL);
INSERT INTO T_TIME VALUES (0, NULL);
INSERT INTO T_TIME VALUES (1, TIME '00:00:00');
INSERT INTO T_TIME VALUES (2, TIME '20:39:21');
INSERT INTO T_TIME VALUES (3, TIME '23:59:59');
INSERT INTO T_TIME VALUES (4, TIME '-1:00:00');
INSERT INTO T_TIME VALUES (5, TIME '838:59:59');
INSERT INTO T_TIME VALUES (6, TIME '-838:59:59');

DROP TABLE IF EXISTS T_YEAR;
CREATE TABLE T_YEAR (ID INT PRIMARY KEY, VALUE YEAR NULL);
INSERT INTO T_YEAR VALUES(0, NULL);
INSERT INTO T_YEAR VALUES(1, '1901');
INSERT INTO T_YEAR VALUES(2, '2012');
INSERT INTO T_YEAR VALUES(3, '2155');
INSERT INTO T_YEAR VALUES(4, '0000');

DROP TABLE IF EXISTS T_YEAR_4;
CREATE TABLE T_YEAR_4 (ID INT PRIMARY KEY, VALUE YEAR(4) NULL);
INSERT INTO T_YEAR_4 VALUES(0, NULL);
INSERT INTO T_YEAR_4 VALUES(1, '1901');
INSERT INTO T_YEAR_4 VALUES(2, '2012');
INSERT INTO T_YEAR_4 VALUES(3, '2155');
INSERT INTO T_YEAR_4 VALUES(4, '0000');

DROP TABLE IF EXISTS T_YEAR_2;
CREATE TABLE T_YEAR_2 (ID INT PRIMARY KEY, VALUE YEAR(2) NULL);
INSERT INTO T_YEAR_2 VALUES(0, NULL);
INSERT INTO T_YEAR_2 VALUES(1, '70');
INSERT INTO T_YEAR_2 VALUES(2, '12');
INSERT INTO T_YEAR_2 VALUES(3, '69');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_CHAR_3;
CREATE TABLE T_CHAR_3 (ID INT PRIMARY KEY, VALUE CHAR(3) NULL);
INSERT INTO T_CHAR_3 VALUES (0, NULL);
INSERT INTO T_CHAR_3 VALUES (1, '   ');
INSERT INTO T_CHAR_3 VALUES (2, 'AOU');
INSERT INTO T_CHAR_3 VALUES (3, 'ÄÖÜ');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_CHAR;
CREATE TABLE T_CHAR (ID INT PRIMARY KEY, VALUE CHAR NULL);
INSERT INTO T_CHAR VALUES (0, NULL);
INSERT INTO T_CHAR VALUES (1, ' ');
INSERT INTO T_CHAR VALUES (2, 'A');
INSERT INTO T_CHAR VALUES (3, 'Ä');

DROP TABLE IF EXISTS T_CHARACTER;
CREATE TABLE T_CHARACTER (ID INT PRIMARY KEY, VALUE CHARACTER NULL);
INSERT INTO T_CHARACTER VALUES (0, NULL);
INSERT INTO T_CHARACTER VALUES (1, ' ');
INSERT INTO T_CHARACTER VALUES (2, 'A');
INSERT INTO T_CHARACTER VALUES (3, 'Ä');

DROP TABLE IF EXISTS T_NATIONAL_CHARACTER;
CREATE TABLE T_NATIONAL_CHARACTER (ID INT PRIMARY KEY, VALUE NATIONAL CHARACTER NULL);
INSERT INTO T_NATIONAL_CHARACTER VALUES (0, NULL);
INSERT INTO T_NATIONAL_CHARACTER VALUES (1, ' ');
INSERT INTO T_NATIONAL_CHARACTER VALUES (2, 'A');
INSERT INTO T_NATIONAL_CHARACTER VALUES (3, 'Ä');

DROP TABLE IF EXISTS T_NCHAR;
CREATE TABLE T_NCHAR (ID INT PRIMARY KEY, VALUE NCHAR NULL);
INSERT INTO T_NCHAR VALUES (0, NULL);
INSERT INTO T_NCHAR VALUES (1, ' ');
INSERT INTO T_NCHAR VALUES (2, 'A');
INSERT INTO T_NCHAR VALUES (3, 'Ä');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_VARCHAR;
CREATE TABLE T_VARCHAR (ID INT PRIMARY KEY, VALUE VARCHAR(100) NULL);
INSERT INTO T_VARCHAR VALUES (0, NULL);
INSERT INTO T_VARCHAR VALUES (1, '');
INSERT INTO T_VARCHAR VALUES (2, '   ');
INSERT INTO T_VARCHAR VALUES (3, 'AOU');
INSERT INTO T_VARCHAR VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_NATIONAL_VARCHAR;
CREATE TABLE T_NATIONAL_VARCHAR (ID INT PRIMARY KEY, VALUE NATIONAL VARCHAR(100) NULL);
INSERT INTO T_NATIONAL_VARCHAR VALUES (0, NULL);
INSERT INTO T_NATIONAL_VARCHAR VALUES (1, '');
INSERT INTO T_NATIONAL_VARCHAR VALUES (2, '   ');
INSERT INTO T_NATIONAL_VARCHAR VALUES (3, 'AOU');
INSERT INTO T_NATIONAL_VARCHAR VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_NVARCHAR;
CREATE TABLE T_NVARCHAR (ID INT PRIMARY KEY, VALUE NVARCHAR(100) NULL);
INSERT INTO T_NVARCHAR VALUES (0, NULL);
INSERT INTO T_NVARCHAR VALUES (1, '');
INSERT INTO T_NVARCHAR VALUES (2, '   ');
INSERT INTO T_NVARCHAR VALUES (3, 'AOU');
INSERT INTO T_NVARCHAR VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_TINYTEXT;
CREATE TABLE T_TINYTEXT (ID INT PRIMARY KEY, VALUE TINYTEXT NULL);
INSERT INTO T_TINYTEXT VALUES (0, NULL);
INSERT INTO T_TINYTEXT VALUES (1, '');
INSERT INTO T_TINYTEXT VALUES (2, '   ');
INSERT INTO T_TINYTEXT VALUES (3, 'AOU');
INSERT INTO T_TINYTEXT VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_TEXT;
CREATE TABLE T_TEXT (ID INT PRIMARY KEY, VALUE TEXT NULL);
INSERT INTO T_TEXT VALUES (0, NULL);
INSERT INTO T_TEXT VALUES (1, '');
INSERT INTO T_TEXT VALUES (2, '   ');
INSERT INTO T_TEXT VALUES (3, 'AOU');
INSERT INTO T_TEXT VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_MEDIUMTEXT;
CREATE TABLE T_MEDIUMTEXT (ID INT PRIMARY KEY, VALUE MEDIUMTEXT NULL);
INSERT INTO T_MEDIUMTEXT VALUES (0, NULL);
INSERT INTO T_MEDIUMTEXT VALUES (1, '');
INSERT INTO T_MEDIUMTEXT VALUES (2, '   ');
INSERT INTO T_MEDIUMTEXT VALUES (3, 'AOU');
INSERT INTO T_MEDIUMTEXT VALUES (4, 'ÄÖÜ');

DROP TABLE IF EXISTS T_LONGTEXT;
CREATE TABLE T_LONGTEXT (ID INT PRIMARY KEY, VALUE LONGTEXT NULL);
INSERT INTO T_LONGTEXT VALUES (0, NULL);
INSERT INTO T_LONGTEXT VALUES (1, '');
INSERT INTO T_LONGTEXT VALUES (2, '   ');
INSERT INTO T_LONGTEXT VALUES (3, 'AOU');
INSERT INTO T_LONGTEXT VALUES (4, 'ÄÖÜ');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_BINARY_4;
CREATE TABLE T_BINARY_4 (ID INT PRIMARY KEY, VALUE BINARY(4) NULL);
INSERT INTO T_BINARY_4 VALUES (0, NULL);
INSERT INTO T_BINARY_4 VALUES (1, X'00000000');
INSERT INTO T_BINARY_4 VALUES (2, X'FFFFFFFF');
INSERT INTO T_BINARY_4 VALUES (3, X'F001F001');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_BINARY;
CREATE TABLE T_BINARY (ID INT PRIMARY KEY, VALUE BINARY NULL);
INSERT INTO T_BINARY VALUES (0, NULL);
INSERT INTO T_BINARY VALUES (1, X'00');
INSERT INTO T_BINARY VALUES (2, X'01');
INSERT INTO T_BINARY VALUES (3, X'FF');

-- same as for HSQLDB
DROP TABLE IF EXISTS T_VARBINARY;
CREATE TABLE T_VARBINARY (ID INT PRIMARY KEY, VALUE VARBINARY(100) NULL);
INSERT INTO T_VARBINARY VALUES (0, NULL);
INSERT INTO T_VARBINARY VALUES (1, X'');
INSERT INTO T_VARBINARY VALUES (2, X'00');
INSERT INTO T_VARBINARY VALUES (3, X'01');
INSERT INTO T_VARBINARY VALUES (4, X'F001F001F001F001');

DROP TABLE IF EXISTS T_TINYBLOB;
CREATE TABLE T_TINYBLOB (ID INT PRIMARY KEY, VALUE TINYBLOB NULL);
INSERT INTO T_TINYBLOB VALUES (0, NULL);
INSERT INTO T_TINYBLOB VALUES (1, X'');
INSERT INTO T_TINYBLOB VALUES (2, X'00');
INSERT INTO T_TINYBLOB VALUES (3, X'01');
INSERT INTO T_TINYBLOB VALUES (4, X'F001F001F001F001');

DROP TABLE IF EXISTS T_BLOB;
CREATE TABLE T_BLOB (ID INT PRIMARY KEY, VALUE BLOB NULL);
INSERT INTO T_BLOB VALUES (0, NULL);
INSERT INTO T_BLOB VALUES (1, X'');
INSERT INTO T_BLOB VALUES (2, X'00');
INSERT INTO T_BLOB VALUES (3, X'01');
INSERT INTO T_BLOB VALUES (4, X'F001F001F001F001');

DROP TABLE IF EXISTS T_MEDIUMBLOB;
CREATE TABLE T_MEDIUMBLOB (ID INT PRIMARY KEY, VALUE MEDIUMBLOB NULL);
INSERT INTO T_MEDIUMBLOB VALUES (0, NULL);
INSERT INTO T_MEDIUMBLOB VALUES (1, X'');
INSERT INTO T_MEDIUMBLOB VALUES (2, X'00');
INSERT INTO T_MEDIUMBLOB VALUES (3, X'01');
INSERT INTO T_MEDIUMBLOB VALUES (4, X'F001F001F001F001');

DROP TABLE IF EXISTS T_LONGBLOB;
CREATE TABLE T_LONGBLOB (ID INT PRIMARY KEY, VALUE LONGBLOB NULL);
INSERT INTO T_LONGBLOB VALUES (0, NULL);
INSERT INTO T_LONGBLOB VALUES (1, X'');
INSERT INTO T_LONGBLOB VALUES (2, X'00');
INSERT INTO T_LONGBLOB VALUES (3, X'01');
INSERT INTO T_LONGBLOB VALUES (4, X'F001F001F001F001');

DROP TABLE IF EXISTS T_ENUM;
CREATE TABLE T_ENUM (ID INT PRIMARY KEY, VALUE ENUM('foo','bar') NULL);
INSERT INTO T_ENUM VALUES (0, NULL);
INSERT INTO T_ENUM VALUES (1, 'foo');
INSERT INTO T_ENUM VALUES (2, 'bar');

DROP TABLE IF EXISTS T_SET;
CREATE TABLE T_SET (ID INT PRIMARY KEY, VALUE SET('foo','bar') NULL);
INSERT INTO T_SET VALUES (0, NULL);
INSERT INTO T_SET VALUES (1, '');
INSERT INTO T_SET VALUES (2, 'foo');
INSERT INTO T_SET VALUES (3, 'bar');
INSERT INTO T_SET VALUES (4, 'foo,bar');
INSERT INTO T_SET VALUES (5, 'bar,foo');
