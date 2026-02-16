-- Version sécurisée (ne plante pas si la colonne est déjà partie)
ALTER TABLE carts DROP COLUMN IF EXISTS name;