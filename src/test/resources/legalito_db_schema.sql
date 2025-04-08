DROP TABLE IF EXISTS template
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_premium BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP
);
DROP TABLE IF EXISTS public.template
CREATE TABLE templates (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    content_template TEXT NOT NULL,
    language VARCHAR(50),
    created_at TIMESTAMP
);
DROP TABLE IF EXISTS public.generated_letter
CREATE TABLE generated_letter (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    template_id INTEGER REFERENCES templates(id),
    data_json TEXT,
    pdf_url VARCHAR(512),
    created_at TIMESTAMP
);
