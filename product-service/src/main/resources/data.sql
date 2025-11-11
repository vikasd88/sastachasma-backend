-- Clear existing data
delete from lens;

-- Insert initial lens data
INSERT INTO lens (type, price, description) VALUES 
('Single Vision', 0.00, 'Standard single vision lenses for clear vision at all distances'),
('Blue Light Blocking', 499.00, 'Protects eyes from digital screen blue light. Reduces eye strain.'),
('Anti-Reflective', 699.00, 'Reduces glare and reflections for better clarity and appearance'),
('Photochromic', 1299.00, 'Automatically darkens in sunlight and clears indoors'),
('Progressive', 1499.00, 'Multi-focal lenses for seamless vision at all distances'),
('Polarized', 899.00, 'Reduces glare from reflective surfaces like water and snow');
