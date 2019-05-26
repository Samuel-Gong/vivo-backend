-- lectures
insert into lecture
values (1, 'gcm', '2019-05-26 19:56', 'hackathon', 1),
       (2, 'gcm', '2019-01-11 11:11', 'test for comment after', 1),
       -- 3 for test not found lecture
       (4, 'gcm', '2019-12-12 11:11', 'test for comment before', 1),
       (5, 'gcm', '2019-05-26 12:12', 'test for empty comments', 1);

-- comments
insert into comment
values (1, '2019-05-26 19:56:00.100', '1', 'fjj', 'good'),
       (2, '2019-05-26 19:56:00.000', '1', 'ghb', 'wow'),
       (3, '2019-05-26 19:56:00.200', '1', 'gy', 'awesome');