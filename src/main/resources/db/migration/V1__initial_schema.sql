CREATE TABLE tier (
      id SMALLINT PRIMARY KEY AUTO_INCREMENT,
      tier VARCHAR(50) UNIQUE NOT NULL,
      discount_percentage SMALLINT NOT NULL
);

CREATE TABLE customer (
      customer_id SMALLINT PRIMARY KEY AUTO_INCREMENT,
      customer VARCHAR(255) UNIQUE NOT NULL,
      tier_id SMALLINT NOT NULL,

      FOREIGN KEY (tier_id) REFERENCES tier(id)
);