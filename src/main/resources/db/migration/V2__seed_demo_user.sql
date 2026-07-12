INSERT INTO users (id, email, password_hash, role, created_at)
VALUES (
           '11111111-1111-1111-1111-111111111111',
           'demo@demo.com',
           'not-a-real-hash-yet',
           'USER',
           now()
       );