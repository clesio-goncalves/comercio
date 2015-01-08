SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Comercio` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Comercio` ;

-- -----------------------------------------------------
-- Table `Comercio`.`Endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Endereco` (
  `codigo_endereco` INT NOT NULL AUTO_INCREMENT,
  `cep` VARCHAR(10) NOT NULL,
  `nome_logradouro` VARCHAR(100) NOT NULL,
  `numero_logradouro` INT NULL,
  `bairro` VARCHAR(50) NOT NULL,
  `UF` VARCHAR(2) NOT NULL,
  `cidade` VARCHAR(50) NOT NULL,
  `complemento` VARCHAR(100) NULL,
  PRIMARY KEY (`codigo_endereco`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Cliente` (
  `codigo_cliente` INT NOT NULL AUTO_INCREMENT,
  `cpf_cliente` VARCHAR(14) NOT NULL,
  `nome_cliente` VARCHAR(100) NOT NULL,
  `telefone_cliente` VARCHAR(13) NOT NULL,
  `email_cliente` VARCHAR(50) NULL,
  `ativo` TINYINT(1) NOT NULL,
  `endereco_cliente` INT NOT NULL,
  PRIMARY KEY (`codigo_cliente`),
  UNIQUE INDEX `cpf_cliente_UNIQUE` (`cpf_cliente` ASC),
  INDEX `fk_Cliente_Endereco_idx` (`endereco_cliente` ASC),
  CONSTRAINT `fk_Cliente_Endereco1`
    FOREIGN KEY (`endereco_cliente`)
    REFERENCES `Comercio`.`Endereco` (`codigo_endereco`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Cargo` (
  `codigo_cargo` INT NOT NULL AUTO_INCREMENT,
  `nome_cargo` VARCHAR(50) NOT NULL,
  `salario` DECIMAL(10,2) NOT NULL,
  `qnt_horas_semana` INT NOT NULL,
  PRIMARY KEY (`codigo_cargo`),
  UNIQUE INDEX `nome_cargo_UNIQUE` (`nome_cargo` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Comercio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Comercio` (
  `codigo_comercio` INT NOT NULL AUTO_INCREMENT,
  `cnpj_comercio` VARCHAR(18) NOT NULL,
  `inscricao_estadual` VARCHAR(12) NULL DEFAULT 'ISENTO',
  `nome_comercio` VARCHAR(100) NOT NULL,
  `telefone_comercio` VARCHAR(13) NOT NULL,
  `email_comercio` VARCHAR(50) NULL,
  `endereco_comercio` INT NOT NULL,
  PRIMARY KEY (`codigo_comercio`),
  INDEX `fk_Comercio_Endereco1_idx` (`endereco_comercio` ASC),
  UNIQUE INDEX `cnpj_empresa_UNIQUE` (`cnpj_comercio` ASC),
  CONSTRAINT `fk_Empresa_Endereco1`
    FOREIGN KEY (`endereco_comercio`)
    REFERENCES `Comercio`.`Endereco` (`codigo_endereco`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Usuario` (
  `codigo_usuario` INT NULL AUTO_INCREMENT,
  `nome_usuario` VARCHAR(10) NOT NULL,
  `senha_usuario` VARCHAR(10) NOT NULL,
  `nivel_usuario` INT NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  `logado` TINYINT(1) NULL DEFAULT false,
  PRIMARY KEY (`codigo_usuario`),
  UNIQUE INDEX `nome_usuario_UNIQUE` (`nome_usuario` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Funcionario` (
  `codigo_funcionario` INT NOT NULL AUTO_INCREMENT COMMENT '		',
  `cpf_funcionario` VARCHAR(14) NOT NULL,
  `nome_funcionario` VARCHAR(100) NOT NULL,
  `telefone_funcionario` VARCHAR(13) NOT NULL,
  `email_funcionario` VARCHAR(50) NULL,
  `sexo_funcionario` ENUM('M', 'F') NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  `cargo_funcionario` INT NOT NULL,
  `comercio_funcionario` INT NOT NULL,
  `usuario_funcionario` INT NULL,
  PRIMARY KEY (`codigo_funcionario`),
  INDEX `fk_Funcionario_Cargo1_idx` (`cargo_funcionario` ASC),
  INDEX `fk_Funcionario_Comercio1_idx` (`comercio_funcionario` ASC),
  INDEX `fk_Funcionario_Usuario1_idx` (`usuario_funcionario` ASC),
  UNIQUE INDEX `cpf_funcionario_UNIQUE` (`cpf_funcionario` ASC),
  CONSTRAINT `fk_Funcionario_Cargo1`
    FOREIGN KEY (`cargo_funcionario`)
    REFERENCES `Comercio`.`Cargo` (`codigo_cargo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Funcionario_Empresa1`
    FOREIGN KEY (`comercio_funcionario`)
    REFERENCES `Comercio`.`Comercio` (`codigo_comercio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Funcionario_Usuario1`
    FOREIGN KEY (`usuario_funcionario`)
    REFERENCES `Comercio`.`Usuario` (`codigo_usuario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Pedido` (
  `codigo_pedido` INT NOT NULL AUTO_INCREMENT,
  `data_pedido` DATE NOT NULL,
  `valor_total_pedido` DECIMAL(10,2) NOT NULL,
  `acrescimo_pedido` DECIMAL(10,2) NULL DEFAULT 0,
  `desconto_pedido` DECIMAL(10,2) NULL DEFAULT 0,
  `funcionario_pedido` INT NOT NULL,
  `cliente_pedido` INT NOT NULL,
  PRIMARY KEY (`codigo_pedido`),
  INDEX `fk_Pedido_Funcionario1_idx` (`funcionario_pedido` ASC),
  INDEX `fk_Pedido_Cliente1_idx` (`cliente_pedido` ASC),
  CONSTRAINT `fk_Pedido_Funcionario1`
    FOREIGN KEY (`funcionario_pedido`)
    REFERENCES `Comercio`.`Funcionario` (`codigo_funcionario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Pedido_Cliente1`
    FOREIGN KEY (`cliente_pedido`)
    REFERENCES `Comercio`.`Cliente` (`codigo_cliente`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Categoria` (
  `codigo_categoria` INT NOT NULL AUTO_INCREMENT,
  `nome_categoria` VARCHAR(50) NOT NULL,
  `percentual_lucro` DECIMAL(10,2) NOT NULL,
  `descricao_categoria` TEXT NOT NULL,
  PRIMARY KEY (`codigo_categoria`),
  UNIQUE INDEX `nome_categoria_UNIQUE` (`nome_categoria` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Produto` (
  `codigo_produto` INT NOT NULL AUTO_INCREMENT,
  `nome_produto` VARCHAR(100) NOT NULL,
  `valor_unitario` DECIMAL(10,2) NULL,
  `quantidade_atual` INT NULL,
  `estoque_minimo` INT NOT NULL DEFAULT 0,
  `ativo` TINYINT(1) NOT NULL,
  `categoria_produto` INT NOT NULL,
  PRIMARY KEY (`codigo_produto`),
  INDEX `fk_Produto_Categoria1_idx` (`categoria_produto` ASC),
  UNIQUE INDEX `nome_produto_UNIQUE` (`nome_produto` ASC),
  CONSTRAINT `fk_Produto_Categoria1`
    FOREIGN KEY (`categoria_produto`)
    REFERENCES `Comercio`.`Categoria` (`codigo_categoria`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Item_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Item_pedido` (
  `codigo_pedido` INT NOT NULL,
  `codigo_produto` INT NOT NULL,
  `quantidade_item` INT NOT NULL,
  `valor_unitario_pedido` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`codigo_pedido`, `codigo_produto`),
  INDEX `fk_Compra_has_Produto_Produto1_idx` (`codigo_produto` ASC),
  INDEX `fk_Compra_has_Produto_Compra1_idx` (`codigo_pedido` ASC),
  CONSTRAINT `fk_Compra_has_Produto_Compra1`
    FOREIGN KEY (`codigo_pedido`)
    REFERENCES `Comercio`.`Pedido` (`codigo_pedido`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Compra_has_Produto_Produto1`
    FOREIGN KEY (`codigo_produto`)
    REFERENCES `Comercio`.`Produto` (`codigo_produto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Fornecedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Fornecedor` (
  `codigo_fornecedor` INT NOT NULL AUTO_INCREMENT,
  `cnpj_fornecedor` VARCHAR(19) NOT NULL,
  `nome_fornecedor` VARCHAR(100) NOT NULL,
  `telefone_fornecedor` VARCHAR(13) NOT NULL,
  `email_fornecedor` VARCHAR(50) NULL,
  `endereco_fornecedor` INT NOT NULL,
  PRIMARY KEY (`codigo_fornecedor`),
  UNIQUE INDEX `cnpj_fornecedor_UNIQUE` (`cnpj_fornecedor` ASC),
  INDEX `fk_Fornecedor_Endereco1_idx` (`endereco_fornecedor` ASC),
  CONSTRAINT `fk_Fornecedor_Endereco1`
    FOREIGN KEY (`endereco_fornecedor`)
    REFERENCES `Comercio`.`Endereco` (`codigo_endereco`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Contato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Contato` (
  `cliente_contato` INT NOT NULL,
  `nome_contato` VARCHAR(50) NOT NULL,
  `telefone_contato` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`cliente_contato`, `nome_contato`),
  INDEX `fk_Contato_Cliente1_idx` (`cliente_contato` ASC),
  CONSTRAINT `fk_Contato_Cliente1`
    FOREIGN KEY (`cliente_contato`)
    REFERENCES `Comercio`.`Cliente` (`codigo_cliente`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Compra` (
  `codigo_compra` INT NOT NULL AUTO_INCREMENT,
  `valor_total_compra` DECIMAL(10,2) NOT NULL,
  `data_compra` DATE NOT NULL,
  `desconto_compra` DECIMAL(10,2) NULL,
  `funcionario_compra` INT NOT NULL,
  `fornecedor_compra` INT NOT NULL,
  PRIMARY KEY (`codigo_compra`),
  INDEX `fk_Entrada_produto_Funcionario1_idx` (`funcionario_compra` ASC),
  INDEX `fk_Compra_Fornecedor1_idx` (`fornecedor_compra` ASC),
  CONSTRAINT `fk_Entrada_produto_Funcionario1`
    FOREIGN KEY (`funcionario_compra`)
    REFERENCES `Comercio`.`Funcionario` (`codigo_funcionario`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Compra_Fornecedor1`
    FOREIGN KEY (`fornecedor_compra`)
    REFERENCES `Comercio`.`Fornecedor` (`codigo_fornecedor`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Comercio`.`Item_compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Comercio`.`Item_compra` (
  `codigo_compra` INT NOT NULL,
  `codigo_produto` INT NOT NULL,
  `quantidade_item` INT NOT NULL,
  `valor_unitario_compra` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`codigo_compra`, `codigo_produto`),
  INDEX `fk_Item_compra_Produto1_idx` (`codigo_produto` ASC),
  CONSTRAINT `fk_Item_compra_Compra1`
    FOREIGN KEY (`codigo_compra`)
    REFERENCES `Comercio`.`Compra` (`codigo_compra`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Item_compra_Produto1`
    FOREIGN KEY (`codigo_produto`)
    REFERENCES `Comercio`.`Produto` (`codigo_produto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
