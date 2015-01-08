-- ENDEREÇO

-- Consulta todos os endereços pelo código
select * from Endereco
where codigo_endereco=?
order by codigo_endereco;

-- Consulta todos os endereços pelo cep
select * from Endereco
where cep like '%?%'
order by cep;

-- Consulta todos os endereços pelo logradouro
select * from Endereco
where nome_logradouro like '%?%'
order by nome_logradouro;

-- Consulta todos os endereços pelo número
select * from Endereco
where numero_logradouro=?
order by numero_logradouro;

-- Consulta todos os endereços pelo bairro
select * from Endereco
where bairro like '%?%'
order by bairro;

-- Consulta todos os endereços pelo estado
select * from Endereco
where uf like '?'
order by uf;

-- Consulta todos os endereços pela cidade
select * from Endereco
where cidade like '%?%'
order by cidade;


-- verificar se um endereço está ligado a um cliente
select count(*) from Endereco as e
inner join Cliente as c on c.endereco_cliente = e.codigo_endereco
where e.codigo_endereco=?;

-- verificar se um endereço está ligado a um fornecedor
select count(*) from Endereco as e
inner join Fornecedor as f on f.endereco_fornecedor = e.codigo_endereco
where e.codigo_endereco=?;

-- verificar se um endereço está ligado a um comércio
select count(*) from Endereco as e
inner join Comercio as c on c.endereco_comercio = e.codigo_endereco
where e.codigo_endereco=?;

-- Consulta se há endereços cadastrados
select count(*) from Endereco;

-- USUÁRIO

-- Consulta todos os usuários pelo código
select * from Usuario
where codigo_usuario=?
order by codigo_usuario;

-- Consulta todos os usuários pelo nome
select * from Usuario
where nome_usuario like '%?%'
order by nome_usuario;

-- Consulta todos os usuários pelo nível
select * from Usuario
where nivel_usuario=?
order by nivel_usuario;

-- Consultar o código do usuário pelo nome
SELECT codigo_usuario FROM Usuario WHERE nome_usuario like '?';

-- Consulta se há usuários cadastrados
select count(*) from Usuario;

-- Consultar o nome do usuário que não esteja ligado a nenhum funcionário
SELECT u.nome_usuario FROM Usuario as u 
where u.codigo_usuario not in (select f.usuario_funcionario as codigo_usuario from Funcionario as f where f.usuario_funcionario is not null)
ORDER BY u.nome_usuario DESC;

-- consultar se usuário está ligado a um funcionário
select u.nome_usuario from Usuario as u
inner join Funcionario as f on u.codigo_usuario = f.usuario_funcionario
where u.nome_usuario like '?';

-- Consultar se um determinado usuário é de nível 3
select * from Usuario 
where nome_usuario like '?' and nivel_usuario=3;

-- Consultar a quantidade de usuários de nível 3 e ativo=true
select count(*) from Usuario 
where nivel_usuario=3 and ativo=true;

-- Consultar a quantidade de usuários inativos
select count(*) from Usuario where ativo=false;

-- Consultar a quantidade de usuários ativos
select count(*) from Usuario where ativo=true;

-- Consultar se usuário está ativo ou inativo
select ativo from Usuario where nome_usuario like '?';	

-- Consultar se usuário pertence a um funcionário ativo ou inativo
select f.ativo from Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario 
where u.nome_usuario like '?';

-- verificar se um usuário está ligado a um funcionário de numa venda
select count(*) from Usuario as u
inner join Funcionario as f on f.usuario_funcionario = u.codigo_usuario
inner join Pedido as p on p.funcionario_pedido = f.codigo_funcionario
where u.codigo_usuario=?;

-- verificar se um usuário está ligado a um funcionário de numa compra
select count(*) from Usuario as u
inner join Funcionario as f on f.usuario_funcionario = u.codigo_usuario
inner join Compra as co on co.funcionario_compra = f.codigo_funcionario
where u.codigo_usuario=?;

-- Listar todos os usuário inativos
select * from Usuario
where ativo=false;

-- Consultar o nome do usuário pelo código
SELECT nome_usuario FROM Usuario WHERE codigo_usuario=?;

-- Consultar o usuário pelo nome e nível 3
select nivel_usuario from Usuario where nome_usuario like '?' and nivel_usuario=3;

-- Consultar nome do usuário logado
select nome_usuario from Usuario where logado=true;

-- Consultar nível do usuário logado
select nivel_usuario from Usuario where logado=true;

-- Alterar logado do usuário
UPDATE Usuario SET logado=? WHERE nome_usuario like '';

-- Alterar logado do usuário
UPDATE Usuario SET logado=false WHERE nome_usuario like '(select nome_usuario from Usuario where logado=true)';

-- CARGO

-- Consulta todos os cargos pelo código
select * from Cargo
where codigo_cargo=?
order by codigo_cargo;

-- Consulta todos os cargos pelo nome
select * from Cargo
where nome_cargo like '%?%'
order by nome_cargo;

-- Consulta todos os cargos pelo salário
select * from Cargo
where salario=?
order by salario;

-- Consulta todos os cargos pelo QntHorasSemana
select * from Cargo
where qnt_horas_semana=?
order by qnt_horas_semana;

-- Consulta nome do cargo pelo nome do usuário
select c.nome_cargo from Cargo as c
inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where u.nome_usuario like '?';

-- verificar se um cargo está ligado a um funcionário de numa venda
select count(*) from Cargo as c
inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo
inner join Pedido as p on p.funcionario_pedido = f.codigo_funcionario
where c.codigo_cargo=?;

-- verificar se um cargo está ligado a um funcionário de numa compra
select count(*) from Cargo as c
inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo
inner join Compra as co on co.funcionario_compra = f.codigo_funcionario
where c.codigo_cargo=?;

-- CLIENTE

-- Consulta todos os clientes pelo código
select * from Cliente
where codigo_cliente=?
order by codigo_cliente;

-- Consulta todos os clientes pelo cpf_cliente
select * from Cliente
where cpfCnpj_cliente like '%?%'
order by cpfCnpj_cliente;

-- Consulta todos os clientes pelo nome
select * from Cliente
where nome_cliente like '%?%'
order by nome_cliente;

-- Consulta todos os clientes pelo telefone_cliente
select * from Cliente
where telefone_cliente like '%?%'
order by telefone_cliente;

-- Consulta todos os clientes pelo email_cliente
select * from Cliente
where email_cliente like '%?%'
order by email_cliente;

-- Consulta todos os clientes pelo endereco_cliente
select * from Cliente
where endereco_cliente=?
order by endereco_cliente;

-- Listar clientes inativos
SELECT * from Cliente where ativo=false;

-- Verificar se um cliente está ativo ou inativo de acordo com o cpf
SELECT ativo from Cliente
where cpf_cliente='?';

-- Verificar se um cliente está ativo ou inativo de acordo com o código
SELECT ativo from Cliente
where codigo_cliente=?;

-- Selecionar clientes pelo codigo_cliente
select * from Cliente
where codigo_cliente=?;

-- Quantidade de clientes inativos
select count(*) from Funcionario where ativo=false;

-- verificar se um cliente está numa venda
select count(*) from Pedido as p
inner join Cliente as c on c.codigo_cliente = p.cliente_pedido
where c.codigo_cliente=?;

-- FORNECEDOR

-- Consulta todos os fornecedores pelo código_fornecedor
select * from Fornecedor
where codigo_fornecedor=?
order by codigo_fornecedor;

-- Consulta todos os fornecedores pelo cnpj_fornecedor
select * from Fornecedor
where cnpj_fornecedor like '%?%'
order by cpfCnpj_fornecedor;

-- Consulta todos os fornecedores pelo nome_fornecedor
select * from Fornecedor
where nome_fornecedor like '%?%'
order by nome_fornecedor;

-- Consulta todos os fornecedores pelo telefone_fornecedor
select * from Fornecedor
where telefone_fornecedor like '%?%'
order by telefone_fornecedor;

-- Consulta todos os fornecedores pelo email_fornecedor
select * from Fornecedor
where email_fornecedor like '%?%'
order by email_fornecedor;

-- Consulta todos os fornecedores pelo endereco_fornecedor
select * from Fornecedor
where endereco_fornecedor=?
order by endereco_fornecedor;

-- verificar se um fornecedor está numa compra
select count(*) from Compra as c
inner join Fornecedor as f on f.codigo_fornecedor = c.fornecedor_compra
where f.codigo_fornecedor=?;

-- COMÉRCIO

-- Consulta todos os comércios pelo código_comercio
select codigo_comercio from Comercio
where codigo_comercio=?
order by codigo_comercio;

-- Consulta todos os comércios pelo cnpj_cliente
select nome_usuario from Comercio
where nome_usuario like '%?%'
order by nome_usuario;

-- Consulta todos os comércios pela inscricao_estadual
select inscricao_estadual from Comercio
where inscricao_estadual like '%?%'
order by inscricao_estadual;

-- Consulta todos os comércios pelo nome_comercio
select nome_comercio from Comercio
where nome_comercio like '%?%'
order by nome_comercio;

-- Consulta todos os comércios pelo telefone_comercio
select telefone_comercio from Comercio
where telefone_comercio like '%?%'
order by telefone_comercio;

-- Consulta todos os comércios pelo email_comercio
select email_comercio from Comercio
where email_comercio like '%?%'
order by email_comercio;

-- Consulta todos os comércios pelo endereco_comercio
select endereco_comercio from Comercio
where endereco_comercio=?
order by endereco_comercio;

-- CONTATO

-- Consulta todos os contatos pelo cliente_contato
select * from Contato
where cliente_contato=?
order by cliente_contato;

-- Consulta todos os contatos pelo nome_contato
select * from Contato
where nome_contato like '%?%'
order by nome_contato;

-- Consulta todos os comércios pelo telefone_comercio
select * from Contato
where telefone_contato like '%?%'
order by telefone_contato;

-- CATEGORIA

-- Consulta todos as categorias pelo codigo_categoria
select * from Categoria
where codigo_categoria=?
order by codigo_categoria;

-- Consulta todos as categorias pelo nome_categoria
select * from Categoria
where nome_categoria like '%?%'
order by nome_categoria;

-- Consulta todos as categorias pelo percentual_lucro
select * from Categoria
where percentual_lucro=?
order by percentual_lucro;

-- Consulta todos as categorias pelo descricao_categoria
select * from Categoria
where descricao_categoria like '%?%'
order by descricao_categoria;

-- Selecionar o percentual da categoria com base no código do produto
select c.percentual_lucro from Categoria as c
inner join Produto as p on p.categoria_produto = c.codigo_categoria
where p.codigo_produto=?;

-- verificar se uma categoria está ligada a um produto de numa venda
select count(*) from Categoria as c
inner join Produto as p on p.categoria_produto = c.codigo_categoria
inner join Item_pedido as ip on ip.codigo_produto = p.codigo_produto
where c.codigo_categoria=?;

-- verificar se uma categoria está ligada a um produto de numa compra
select count(*) from Categoria as c
inner join Produto as p on p.categoria_produto = c.codigo_categoria
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
where c.codigo_categoria=?;


-- FUNCIONÁRIO

-- Consulta todos os funcionários pelo codigo_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.codigo_funcionario=?
order by f.codigo_funcionario;

-- Consulta todos os funcionários pelo cpf_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.cpf_funcionario like '%?%'
order by f.cpf_funcionario;

-- Consulta todos os funcionários pelo nome_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.nome_funcionario like '%?%'
order by f.nome_funcionario;

-- Consulta todos os funcionários pela telefone_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.telefone_funcionario like '%?%'
order by f.telefone_funcionario;

-- Consulta todos os funcionários pelo email_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.email_funcionario like '%?%'
order by f.email_funcionario;

-- Consulta todos os funcionários pelo sexo_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.sexo_funcionario like '?'
order by f.sexo_funcionario;

-- Consulta todos os funcionários pelo cargo_funcionario
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where c.nome_cargo like '%?%'
order by c.nome_cargo;

-- Consulta todos os funcionários pelo usuario_funcionario
select f.*, u.nome_usuario, c.nome_cargo from Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where u.nome_usuario like '%?%'
order by u.nome_usuario;

-- Listar todos os dados dos funcionários, nome do usuário e nome do cargo
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario;

-- Listar todos os dados dos funcionários inativos
select f.*, c.nome_cargo from Funcionario as f
inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario
where f.ativo=false;

-- Consultar o nome do funcionário logado pelo nome do usuário
select f.nome_funcionario from Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where u.nome_usuario like '?';

-- Consultar o nome do funcionário logado pelo nome do usuário
select f.codigo_funcionario from Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where u.nome_usuario like '?';

-- Listar funcionário inativos
SELECT * from Funcionario where ativo=false;

-- Consultar se funcionário está inativo pelo cpf
select ativo from Funcionario where cpf_funcionario like '?';

-- Consultar a quantidade de funcionários ativos que esteja ligado ao usuário ativo de nível 3
SELECT count(*) FROM Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where f.ativo=true and u.ativo=true and u.nivel_usuario=3;

-- Consultar se um determinado funcionário está ligado a um usuário ativo de nível 3 (retorna)
select f.* from Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where f.cpf_funcionario like '?' and u.ativo=true and u.nivel_usuario=3;

-- verificar se um funcionário está numa venda
select count(*) from Pedido as p
inner join Funcionario as f on f.codigo_funcionario = p.funcionario_pedido
where f.codigo_funcionario=?;

-- verificar se um funcionário está numa compra
select count(*) from Compra as c
inner join Funcionario as f on f.codigo_funcionario = c.funcionario_compra
where f.codigo_funcionario=?;

-- Quantidade de funcionários inativos
select count(*) from Funcionario where ativo=false;

-- Quantidade de funcionários ligados a pelo menos 1 usuário ativo e nível 3
SELECT count(*) FROM Funcionario as f
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
where u.nivel_usuario=3 and u.ativo=true;

-- Inserir dados do funcionario com base no nome_cargo, no nome_usuario e no nome_comercio
INSERT INTO Funcionario (cpf_funcionario, nome_funcionario, telefone_funcionario, email_funcionario, sexo_funcionario, ativo, cargo_funcionario, comercio_funcionario, usuario_funcionario) 
VALUES (?,?,?,?,?,?,(select codigo_cargo as cargo_funcionario from Cargo where nome_cargo like '?'),(select codigo_comercio as comercio_funcionario from Comercio where nome_comercio like '?'),(select codigo_usuario as usuario_funcionario from Usuario where nome_usuario like '?'));

-- Alterar o registro do funcionário, o nome_cargo e no nome_usuario
UPDATE Funcionario SET nome_funcionario=?, telefone_funcionario=?, email_funcionario=?, sexo_funcionario=?, ativo=?, cargo_funcionario=(select codigo_cargo as cargo_funcionario from Cargo where nome_cargo like '?'), usuario_funcionario=(select codigo_usuario as usuario_funcionario from Usuario where nome_usuario like '?') WHERE codigo_funcionario=?;


-- PRODUTO


-- Consulta todos os produtos pelo codigo_produto
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where p.codigo_produto=?
order by p.codigo_produto;

-- Consulta todos os produtos pelo nome_produto
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where p.nome_produto like '%?%'
order by p.nome_produto;

-- Consulta todos os produtos pelo valor_unitario
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where p.valor_unitario=?
order by p.valor_unitario;

-- Consulta todos os produtos pelo nome da categoria
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where c.nome_categoria like '%?%'
order by c.nome_categoria;

-- Consulta todos os produtos pelo fornecedor_produto
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where p.fornecedor_produto=?
order by p.fornecedor_produto;

-- Listar todos os dados dos produtos, nome da categoria
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto;

-- Consultar todos os dados dos produtos com base no código do produto
select distinct(co.fornecedor_compra), p.*, c.nome_categoria from Produto as p
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
inner join Compra as co on co.codigo_compra = ic.codigo_compra
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where co.fornecedor_compra=?;

-- Listar todos os dados dos produtos inativos
select p.*, c.nome_categoria from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
where p.ativo=false;

-- Selecionar o nome de todos os produtos ativos ordenados pelo nome
SELECT nome_produto FROM Produto 
where ativo=true
ORDER BY nome_produto;

-- Listar produtos inativos
SELECT * from Produto where ativo=false;

-- Verificar se um produto está ativo ou inativo de acordo com o nome_produto
SELECT ativo from Produto
where nome_produto='?';

-- Verificar se um produto está ativo ou inativo de acordo com o código
SELECT ativo from Produto
where codigo_produto=?;

-- Pega a quantidade de um produto com base no código
select quantidade_atual from Produto
where codigo_produto=?;

-- Pega o esqoque mímimo de um produto com base no código
select estoque_minimo from Produto
where codigo_produto=?;

-- Quantidade de produtos inativos
select count(*) from Produto where ativo=false;

-- Inserir dados do produto com base no nome_categoria
INSERT INTO Produto (nome_produto, valor_unitario, quantidade_atual, estoque_minimo, ativo, fornecedor_produto, categoria_produto) 
VALUES (?,?,?,?,?,?,(select codigo_categoria as categoria_produto from Categoria where nome_categoria like '?'));

-- Altera a quantidade de um produto de acordo com o código
update Produto set quantidade_atual=?
where codigo_produto=?;

-- retorna o valor unitário a ser atualizado
select ic.valor_unitario_compra + (ic.valor_unitario_compra * (c.percentual_lucro/100)) as valor_unitario 
from Produto as p
inner join Categoria as c on c.codigo_categoria = p.categoria_produto
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
where ic.codigo_produto=? and ic.codigo_compra = (select max(codigo_compra) from Item_compra);

-- retorna o preço de custo do produto na última compra
select ic.valor_unitario_compra from Produto as p
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
where p.codigo_produto=? and ic.codigo_compra = (select max(codigo_compra) from Item_compra where codigo_produto=?);

-- retorna a data da última compra do produto
select c.data_compra from Produto as p
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
inner join Compra as c on c.codigo_compra = ic.codigo_compra
where p.codigo_produto=1 and c.codigo_compra = 
(select max(c.codigo_compra) from Compra as c
	inner join Item_compra as ic on ic.codigo_compra = c.codigo_compra
	where ic.codigo_produto=1);

-- verificar se um produto está numa venda
select count(*) from Produto as p
inner join Item_pedido as ip on ip.codigo_produto = p.codigo_produto
where p.codigo_produto=?;

-- verificar se um produto está numa compra
select count(*) from Produto as p
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto
where p.codigo_produto=?;

-- Alterar o registro do produto e codigo_categoria com base no nome da categoria
UPDATE Produto SET valor_unitario=?, estoque_minimo=?, ativo=?, fornecedor_produto=?, categoria_produto=(select codigo_categoria as categoria_produto from Categoria where nome_categoria like '?') WHERE codigo_produto=?;


-- PEDIDO

-- Selecionar todos as datas e códigos dos pedidos ordenados pela data mais atual

select codigo_pedido, data_pedido from Pedido
order by data_pedido desc, codigo_pedido desc;

-- Selecionar todos os dados dos pedidos
-- do funcionario, do usuário, do cliente
select p.codigo_pedido, p.acrescimo_pedido, p.desconto_pedido, p.valor_total_pedido, 
f.nome_funcionario, u.nome_usuario, cl.nome_cliente from Pedido as p
inner join Funcionario as f on f.codigo_funcionario = p.funcionario_pedido
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
inner join Cliente as cl on cl.codigo_cliente = p.cliente_pedido
where p.codigo_pedido=?;

-- selecionar os dados dos ítens_pedidos
select ip.codigo_produto, ip.codigo_pedido, ip.quantidade_item, 
p.nome_produto, ip.valor_unitario_pedido from Item_pedido as ip
inner join Produto as p on p.codigo_produto = ip.codigo_produto
where ip.codigo_pedido=?;

-- Selecionar o lucro com base no código do produto e no código do pedido
select ip.quantidade_item * (ip.valor_unitario_pedido - ic.valor_unitario_compra) as lucro 
from Item_pedido as ip
inner join Produto as p on p.codigo_produto = ip.codigo_produto
inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto 
where ip.codigo_pedido=? and ic.codigo_compra in
(select max(codigo_compra) from Item_compra where codigo_produto=?) and p.codigo_produto=?;

-- Pesquisar os pedidos pela data, codigo_pedido, codigo_cliente, codigo_produto
insert into Pesquisa
	select distinct(p.codigo_pedido) as codigo, p.data_pedido as data
	from Pedido as p
	inner join Item_pedido as ip on ip.codigo_pedido = p.codigo_pedido
	where p.data_pedido between '?' and '?' and p.codigo_pedido=? and p.cliente_pedido=? and ip.codigo_produto=?
	order by p.data_pedido desc, p.codigo_pedido desc;



delete from Pesquisa;


create table Pesquisa(
  codigo INT NOT NULL,
  data DATE NOT NULL
);




-- COMPRA

-- Selecionar todos as datas e códigos das compras ordenadas pela data mais atual

select codigo_compra, data_compra from Compra
order by data_compra desc, codigo_compra desc;

-- Selecionar todos os dados das compras
-- do funcionario, do usuário, do fornecedor
select c.codigo_compra, c.desconto_compra, c.valor_total_compra, 
f.nome_funcionario, u.nome_usuario, fo.nome_fornecedor from Compra as c
inner join Funcionario as f on f.codigo_funcionario = c.funcionario_compra
inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario
inner join Fornecedor as fo on fo.codigo_fornecedor = c.fornecedor_compra
where c.codigo_compra=?;

-- selecionar os dados dos ítens_compra
select ic.codigo_produto, ic.codigo_compra, ic.quantidade_item, 
p.nome_produto, ic.valor_unitario_compra from Item_compra as ic
inner join Produto as p on p.codigo_produto = ic.codigo_produto
where ic.codigo_compra=?;


-- Pesquisar as compras pela data, codigo_compra, codigo_fornecedor, codigo_produto
insert into Pesquisa
	select distinct(c.codigo_compra) as codigo, c.data_compra as data
	from Compra as c
	inner join Item_compra as ic on ic.codigo_compra = c.codigo_compra
	where c.data_compra between '?' and '?' and c.codigo_compra=? and c.fornecedor_compra=? and ic.codigo_produto=?
	order by c.data_compra desc, c.codigo_compra desc;


