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
    profitMargin DOUBLE PRECISION DEFAULT NULL,
    VATRate DOUBLE PRECISION DEFAULT NULL,
    totalCost DOUBLE PRECISION ,
    area DOUBLE PRECISION NOT NULL,
    projectStatus projectStatus NOT NULL,
    client_id UUID REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TYPE componentType AS ENUM ('labor', 'material');

CREATE TABLE components (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    unitName VARCHAR(100) NOT NULL,
    componentType ComponentType NOT NULL,
    vatRate DOUBLE PRECISION,
    project_id UUID REFERENCES Projects(Id) ON DELETE CASCADE
);

CREATE TABLE labors (
    hourlyRate DOUBLE PRECISION NOT NULL,
    workingHours DOUBLE PRECISION NOT NULL,
    workerProductivity DOUBLE PRECISION NOT NULL
) INHERITS (components);

CREATE TABLE materials (
    transportCost DOUBLE PRECISION NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    qualityCoefficient DOUBLE PRECISION NOT NULL,
	unitCost DOUBLE PRECISION
) INHERITS (components);



CREATE TABLE quotations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estimatedAmount DOUBLE PRECISION NOT NULL,
    issueDate DATE NOT NULL,
     validityDate DATE NOT NULL,
     accepted BOOLEAN DEFAULT FALSE,
     project_id UUID REFERENCES Projects(Id) ON DELETE CASCADE

);

