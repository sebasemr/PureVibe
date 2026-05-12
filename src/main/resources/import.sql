INSERT INTO roles (id, authority) VALUES (1, 'ROLE_USER') ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, authority) VALUES (2, 'ROLE_FAMILIAR') ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, authority) VALUES (3, 'ROLE_INSTITUCION') ON CONFLICT (id) DO NOTHING;

INSERT INTO familias (id, nombre, codigo_invitacion) VALUES (1, 'Los Pérez', 'FAM-1234') ON CONFLICT (id) DO NOTHING;
INSERT INTO instituciones (id, nombre, tipo, codigo_invitacion) VALUES (1, 'Colegio San Agustín', 'Colegio', 'INST-9999') ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, username, email, password, huella_total_kg_co2e, huella_transporte_kg, huella_energia_kg, huella_alimentacion_kg, huella_residuos_kg) VALUES (1, 'AdrianLS', 'adrian@eco.com', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 3500.00, 1200.00, 1000.00, 800.00, 500.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO users(id, username, email, password, huella_total_kg_co2e, huella_transporte_kg, huella_energia_kg, huella_alimentacion_kg, huella_residuos_kg) VALUES (2, 'CarlosEco', 'carlos@eco.com', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 2800.00, 800.00, 900.00, 900.00, 200.00) ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, username, email, password, familia_id, huella_total_kg_co2e, huella_transporte_kg, huella_energia_kg, huella_alimentacion_kg, huella_residuos_kg) VALUES (3, 'PapaPerez', 'papa@perez.com', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, 4500.00, 2000.00, 1500.00, 800.00, 200.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO users(id, username, email, password, familia_id, huella_total_kg_co2e, huella_transporte_kg, huella_energia_kg, huella_alimentacion_kg, huella_residuos_kg) VALUES (4, 'MamaPerez', 'mama@perez.com', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, 3200.00, 1000.00, 1200.00, 900.00, 100.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO users(id, username, email, password, familia_id, huella_total_kg_co2e) VALUES (5, 'HijoPerez', 'hijo@perez.com', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, null) ON CONFLICT (id) DO NOTHING;

INSERT INTO users(id, username, email, password, institucion_id, huella_total_kg_co2e) VALUES (6, 'DirectorSA', 'director@sa.edu.pe', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, 5000.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO users(id, username, email, password, institucion_id, huella_total_kg_co2e) VALUES (7, 'ProfMartinez', 'martinez@sa.edu.pe', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, 3800.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO users(id, username, email, password, institucion_id, huella_total_kg_co2e) VALUES (8, 'AlumnoJuan', 'juan@sa.edu.pe', '$2a$12$lu7mBMUvnCmsGswFbqtgxunl6qiUePUxtPMe51Di5pQib/PTTzzny', 1, 2500.00) ON CONFLICT (id) DO NOTHING;

UPDATE familias SET admin_user_id = 3 WHERE id = 1;
UPDATE instituciones SET admin_user_id = 6 WHERE id = 1;

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (5, 2) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (6, 3) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (7, 3) ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_id, role_id) VALUES (8, 3) ON CONFLICT DO NOTHING;


INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'auto', 'km', 0.20, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'moto', 'km', 0.12, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'avion', 'km', 0.25, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'bicicleta', 'km', 0.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'a pie', 'km', 0.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('transporte', 'transporte publico', 'km', 0.08, 'demo', true); -- (Cubre "bus", "tren", "metropolitano")

-- 2. ENERGÍA (Para ambas calculadoras)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'electricidad', 'kWh', 0.45, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'calefaccion', 'horas', 1.5, 'demo', true); -- (Para Registro Diario)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'glp', 'kg', 2.90, 'demo', true); -- (¡ESTE ES EL QUE TE FALTABA!)

-- 3. ENERGÍA - DISPOSITIVOS (Para Registro Diario)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'less2', 'dia', 0.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', '2to4', 'dia', 1.0, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('energia', 'more4', 'dia', 2.0, 'demo', true);

-- 4. ALIMENTACIÓN (Para Calculadora Personal)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'omnivora', 'dia', 2.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'vegetariana', 'dia', 1.5, 'demo', true);
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('alimentacion', 'vegana', 'dia', 1.0, 'demo', true);

-- 5. RESIDUOS (Para ambas calculadoras)
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_per_unidad, fuente, vigente) VALUES ('residuos', 'general', 'kg', 0.02, 'demo', true);

-- (Añade esto al final de tu import.sql)
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'Como Reducir tu Huella de Carbono y Cuidar el PLaneta', 'https://www.youtube.com/watch?v=8A4pYULzp9U', 'Wabi-Sabi',  'hogar', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'Reciclaje 101: lo que sí y lo que no', 'https://www.rts.com/es/blog/recycling-101-how-to-recycle-better/', 'Recicyle Track Systems', 'residuos', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('PODCAST', 'Movilidad Sostenible', 'https://open.spotify.com/show/7a4R0MxdrUW4z3jj9VqzyF', 'Ciclogreen',  'transporte', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'Ahorro de Energía en el hogar', 'https://www.youtube.com/watch?v=LyK3F7PLzAg', 'Ministerio de Energia y Minas del Perú',  'energia', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'Compostaje en casa paso a paso', 'https://www.bbva.com/es/sostenibilidad/como-hacer-compost-con-los-residuos-organicos-de-la-casa/', 'BBVA',  'residuos', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('ARTICULO', 'La guía definitiva para no perderte en el mundo de la moda sostenible', 'https://www.vogue.es/moda/articulos/moda-sostenible-que-significa-guia-consejos-marcas', 'Vogue', 'consumo', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('VIDEO', 'El cambio climático y la alimentación saludable', 'https://www.youtube.com/watch?v=jq6u8RoqGOE', 'Canal12Ch', 'alimentacion', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, tema, creado_en) VALUES ('PODCAST', 'Cuidado del Medio Ambiente', 'https://open.spotify.com/show/1F47qzb5Vb5z2NHMa1yh6i', 'Leslie Serna', 'Medio Ambiente', now());

INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuánto tiempo tarda una botella de plástico en descomponerse?', '50 años', '100 años', '450 a 1000 años', 'Nunca se descompone', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué sector consume más agua dulce a nivel mundial?', 'Industria textil', 'Uso doméstico', 'Agricultura', 'Minería', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué es el "consumo vampiro" de energía?', 'Usar la luz de día', 'Aparatos enchufados apagados', 'Cargar el celular de noche', 'Usar baterías recargables', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuál es la forma más eficiente de secar la ropa?', 'Secadora eléctrica', 'Secadora a gas', 'Al sol y al aire', 'Plancharla mojada', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué material se puede reciclar infinitamente sin perder calidad?', 'Plástico', 'Papel', 'Vidrio', 'Cartón', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué dieta tiene generalmente una huella de carbono más baja?', 'Dieta basada en carnes rojas', 'Dieta basada en plantas', 'Dieta de comida rápida', 'Dieta alta en lácteos', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuál es el principal gas de efecto invernadero emitido por los autos?', 'Oxígeno', 'Dióxido de Carbono', 'Helio', 'Metano', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué acción ahorra más agua al lavarse los dientes?', 'Usar agua caliente', 'Cerrar el grifo mientras cepillas', 'Usar un vaso de plástico', 'Cepillarse rápido', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué es el compostaje?', 'Quemar basura', 'Enterrar plástico', 'Descomponer residuos orgánicos', 'Reciclar vidrio', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué porcentaje de la comida producida en el mundo se desperdicia?', 'Casi nada', 'Alrededor del 10%', 'Alrededor del 30%', 'Más del 80%', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué significa "Eficiencia Energética"?', 'No usar energía', 'Usar menos energía para la misma tarea', 'Usar energía solar', 'Apagar todo siempre', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué contenedor se usa internacionalmente para el papel?', 'Azul', 'Verde', 'Amarillo', 'Rojo', 'A', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuál de estos NO es un combustible fósil?', 'Petróleo', 'Carbón', 'Gas Natural', 'Viento', 'D', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuántos litros de agua se necesitan aprox. para hacer un pantalón de jean?', '50 litros', '500 litros', '7,500 litros', '10,000 litros', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué es la obsolescencia programada?', 'Un programa de TV antiguo', 'Productos diseñados para fallar pronto', 'Un método de reciclaje', 'Una ley ambiental', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué país emite más CO2 actualmente?', 'Estados Unidos', 'China', 'India', 'Rusia', 'B', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué puedes hacer con el aceite de cocina usado?', 'Tirarlo al desagüe', 'Tirarlo a la tierra', 'Llevarlo a un punto limpio', 'Quemar', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Cuál es una fuente de energía renovable?', 'Nuclear', 'Carbón', 'Solar', 'Gas', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué bolsa es más ecológica?', 'Plástico de un solo uso', 'Papel de un solo uso', 'Bolsa de tela reutilizable', 'Ninguna', 'C', 10) ON CONFLICT DO NOTHING;
INSERT INTO preguntas_quiz (enunciado, opciona, opcionb, opcionc, opciond, respuesta_correcta, puntos_otorgados) VALUES ('¿Qué animal es vital para la polinización de cultivos?', 'Las abejas', 'Las hormigas', 'Los leones', 'Los peces', 'A', 10) ON CONFLICT DO NOTHING;

SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('actividades_diarias_actividad_id_seq', (SELECT MAX(actividad_id) FROM actividades_diarias));