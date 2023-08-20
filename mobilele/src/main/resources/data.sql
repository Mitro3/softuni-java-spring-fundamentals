-- INSERT INTO users (is_active, id, email, first_name, image_url, last_name, password)
-- VALUES (1, 1, 'mitroshanov@gmail.com', 'Aleksandar', null, 'Mitroshanov', 'topsecret');

-- INSERT INTO brands (id, name)
-- VALUES (1, 'Ford'),
--        (2, 'Toyota');

INSERT INTO models (id, name, category, start_year, end_year, brand_id, img_url)
VALUES (1, 'Fiesta', 'CAR', 1976, null, 1, 'https://upload.wikimedia.org/wikipedia/commons/7/7d/2017_Ford_Fiesta_Zetec_Turbo_1.0_Front.jpg'),
       (2, 'Escort', 'CAR', 1968, 2000, 1, 'https://www.auto-data.net/images/f110/Ford-Escort-VII-Hatch-GAL-AFL.jpg'),
       (3, 'Yaris', 'CAR', 1999, null, 2, 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/2020_Toyota_Yaris_Design_HEV_CVT_1.5_Front.jpg/1280px-2020_Toyota_Yaris_Design_HEV_CVT_1.5_Front.jpg');
