CREATE TABLE clients (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    isProfessional BOOLEAN NOT NULL
);
CREATE TYPE projectStatus AS ENUM ('inProgress', 'completed', 'canceled');

CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    projectName VARCHAR(255) NOT NULL,
    profitMargin DOUBLE PRECISION,
    totalCost DOUBLE PRECISION ,
    area DOUBLE PRECISION NOT NULL,
    projectStatus projectStatus NOT NULL,
    client_id UUID REFERENCES clients(id)
);

