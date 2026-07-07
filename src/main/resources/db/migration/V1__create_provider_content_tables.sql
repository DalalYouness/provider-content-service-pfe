-- Serive table
CREATE TABLE services (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            service_name VARCHAR(100) NOT NULL UNIQUE,
            description TEXT
) ENGINE=InnoDB;
-- Portfolio table ( logical relation between portfolio and profil in identity-service )

CREATE TABLE portfolio_items (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                image_url VARCHAR(255) NOT NULL,
                description VARCHAR(255),
                id_provider BIGINT NOT NULL, -- Logical FK (No physical reference)
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                INDEX (id_provider) -- Optimization
) ENGINE=InnoDB;
-- table associative between profile and service because the relationship between them is many to many

CREATE TABLE expertise (
                id_provider BIGINT NOT NULL, -- Logical FK
                id_service BIGINT NOT NULL,
                PRIMARY KEY (id_provider, id_service),
                CONSTRAINT fk_expertise_service -- Foreign Key Physique
                FOREIGN KEY (id_service) REFERENCES services(id)
                ON DELETE RESTRICT
) ENGINE=InnoDB;
