CREATE TABLE messages (
    id UUID PRIMARY KEY,
    recipient_id UUID NOT NULL,
    sender_id UUID,
    subject VARCHAR(255) NOT NULL,
    content TEXT,
    type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    reference_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_messages_recipient ON messages(recipient_id);