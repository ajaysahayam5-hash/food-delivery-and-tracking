# ER Diagram — Food Delivery (16 tables)

Source: `er.dbml` (paste into dbdiagram.io → Export PNG as `er.png` in same folder).

## Key relationships
- `users` 1—1 `carts`, 1—N `addresses`, `orders`, `reviews`, `favorites`
- `restaurants` 1—N `menu_categories`, `menu_items`, `orders`, `reviews`
- `orders` 1—N `order_items` (junction for M—N `orders`↔`menu_items`), 1—1 `payments`, 1—1 `deliveries`
- `deliveries` 1—N `delivery_locations` (GPS history)
- `delivery_partners.user_id` UNIQUE → `users` (1—1)
- All PKs `id BIGINT`, FKs indexed, `email` UNIQUE, `order_id` UNIQUE in `payments`/`deliveries`.

See `er.dbml` for full PK/FK + types. Matches `database/schema.sql` + JPA entities.
