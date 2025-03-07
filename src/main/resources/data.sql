INSERT INTO accounts (id, username, email, phone_number, password, status, role) VALUES
(1, 'admin01', 'admin@example.com', '0987654321', 'adminpass', 'ACTIVE', 'ADMIN'),
(2, 'publisher01', 'publisher1@example.com', '0912345678', 'pubpass1', 'ACTIVE', 'PUBLISHER'),
(3, 'publisher02', 'publisher2@example.com', '0923456789', 'pubpass2', 'ACTIVE', 'PUBLISHER'),
(4, 'advertiser01', 'advertiser1@example.com', '0934567890', 'advpass1', 'ACTIVE', 'ADVERTISERS'),
(5, 'advertiser02', 'advertiser2@example.com', '0945678901', 'advpass2', 'ACTIVE', 'ADVERTISERS');

INSERT INTO admins (id, dummy_field) VALUES
(1, 'Demo');

INSERT INTO publishers (id, payment_info, referral_code) VALUES
(2, 'Bank Account 123456', 'REF12345'),
(3, 'PayPal: pub2@paypal.com', 'REF67890');

INSERT INTO advertisers (id, company_name, account_balance, billing_info) VALUES
(4, 'Company A', 5000.00, 'Bank Transfer - 7890123'),
(5, 'Company B', 7000.50, 'PayPal: adv2@paypal.com');
