CREATE SEQUENCE IF NOT EXISTS aircraft_seq START 1;

CREATE TABLE IF NOT EXISTS aircraft
(
    id                BIGINT PRIMARY KEY DEFAULT nextval('aircraft_seq'),
    callsign          VARCHAR(255),
    squawk            VARCHAR(255),
    reg               VARCHAR(255),
    flightno          VARCHAR(255),
    route             VARCHAR(255),
    type              VARCHAR(255),
    category          VARCHAR(255),
    altitude          INTEGER,
    heading           INTEGER,
    speed             INTEGER,
    vert_rate         INTEGER,
    selected_altitude INTEGER,
    lat               DOUBLE PRECISION,
    lon               DOUBLE PRECISION,
    barometer         DOUBLE PRECISION,
    polar_distance    DOUBLE PRECISION,
    polar_bearing     DOUBLE PRECISION,
    is_adsb           BOOLEAN,
    is_on_ground      BOOLEAN,
    last_seen_time    TIMESTAMP,
    pos_update_time   TIMESTAMP,
    bds40_seen_time   TIMESTAMP
);
