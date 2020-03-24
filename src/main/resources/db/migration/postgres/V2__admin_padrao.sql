INSERT INTO empresas 
	(cnpj, data_atualizacao, data_criacao, razao_social)
VALUES
	('01209045000133', null, CURRENT_DATE, 'My Empresa Teste');

INSERT INTO funcionarios 
	(cpf, data_atualizacao, data_criacao, email, nome, perfil, qtd_horas_almoco, qtd_horas_trabalho_dia, senha, valor_hora, empresa_id)
VALUES 
	('22957062038', null, CURRENT_DATE, 'admin@myempresa.com', 'ADMIN', 'ROLE_ADMIN', null, null, '$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', null, (SELECT id FROM empresas WHERE cnpj = '01209045000133'));