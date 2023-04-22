## Dependencias

- [Java Developer Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Make

## Comandos make

- `make` ou `make full`: Buildar + Executar
- `make build`: Buildar
- `make run`: Executar
- `make clean`: Limpa os arquivos `.class` gerados no build

## Como rodar

- Na root do repositório use o comando `make` para buildar e executar o programa

## Como utilizar

OBS:

- O sistema já vem inicializado com 2 candidatos a presidente e 3 a deputado federal
- O sistema já vem com os dois gestores (de sessão e de candidaturas)
- O sistema já vem com todos os eleitores possíveis para utilizá-los basta checar o arquivo `voterLoad.txt`

No menu inicial para gerenciar candidatos e eleição siga pela opção 2:

- User: `emp` , Password: `12345` -> Cadastro e remoção de candidatos da eleição
- User: `cert` , Password: `54321` -> Inicialização/finalização da eleição (liberar pra poder votar) e mostrar o resultado ao final da eleição.

Além da senha de usuário é necessário a senha da eleição para completar operações relacionadas a gestão da eleição ou candidatos. Essa senha é a palavra `password`

Para votar também existe um eleitor com o título de eleitor nº 123456789012 que pode votar nos candidatos pré-cadastrados

## Execução teste

Para uma execução teste podemos seguir o seguinte passo:

- Ao iniciar a aplicação selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 1 e inserir a senha da urna (`password`) para iniciar a votação
- Escolher a opção 0 para voltar ao menu inicial
- Escolher votar (opção 1) e inserir o nº `123456789012` do eleitor de teste
- Selecionar sim e votar respectivamente `123` , `12345` e `br`
- Escolher votar (opção 1) e inserir o nº `268888719264` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `123` , `54321` e `12345`
- Escolher votar (opção 1) e inserir o nº `638991919941` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `000` , `12345` e `00000`
- Escolher votar (opção 1) e inserir o nº `965575671024` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `123` , `12345` e `00000`
- No menu inicial, selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 2 e inserir a senha da urna (`password`) para encerrar a votação
- Escolher a opção 3 e inserir a senha da urna (`password`) para mostrar o resultado final da votação
- Escolher a opção 0 duas vezes para encerrar a aplicação

<featureModel>
	<properties>
		<graphics key="autolayoutconstraints" value="false"/>
		<graphics key="legendposition" value="1165,366"/>
		<graphics key="legendautolayout" value="false"/>
		<graphics key="showconstraints" value="true"/>
		<graphics key="showshortnames" value="false"/>
		<graphics key="layout" value="horizontal"/>
		<graphics key="showcollapsedconstraints" value="true"/>
		<graphics key="legendhidden" value="false"/>
		<graphics key="layoutalgorithm" value="0"/>
	</properties>
	<struct>
		<and abstract="true" mandatory="true" name="Urna">
			<graphics key="position" value="453,420"/>
			<and abstract="true" mandatory="true" name="Eleição">
				<graphics key="position" value="528,250"/>
				<graphics key="collapsed" value="false"/>
				<and abstract="true" mandatory="true" name="Exibição">
					<graphics key="position" value="652,83"/>
					<graphics key="collapsed" value="false"/>
					<feature mandatory="true" name="Exibir Eleitos">
						<graphics key="position" value="788,40"/>
					</feature>
					<feature mandatory="true" name="Exibir Votos Nominais">
						<graphics key="position" value="788,69"/>
					</feature>
					<feature name="Exibir Derrotados">
						<graphics key="position" value="788,98"/>
					</feature>
					<feature name="Estatísticas Dinâmicas">
						<graphics key="position" value="788,127"/>
					</feature>
				</and>
				<alt abstract="true" mandatory="true" name="Tipos de Eleição">
					<graphics key="position" value="620,154"/>
					<graphics key="collapsed" value="false"/>
					<feature name="Federal">
						<graphics key="position" value="788,156"/>
					</feature>
					<feature name="Municipal e Estadual">
						<graphics key="position" value="788,185"/>
					</feature>
					<feature name="Universidade">
						<graphics key="position" value="788,214"/>
					</feature>
					<feature name="Reality Show">
						<graphics key="position" value="788,243"/>
					</feature>
				</alt>
				<feature name="Segundo Turno">
					<graphics key="position" value="652,250"/>
				</feature>
				<or mandatory="true" name="Eleitor">
					<graphics key="position" value="652,301"/>
					<graphics key="collapsed" value="false"/>
					<feature name="Titulo de Eleitor">
						<graphics key="position" value="788,272"/>
					</feature>
					<feature name="RG/CPF">
					<graphics key="position" value="788,301"/>
				</feature>
				<feature name="Nome">
					<graphics key="position" value="788,330"/>
				</feature>
			</or>
			<and abstract="true" mandatory="true" name="Votação">
				<graphics key="position" value="652,417"/>
				<graphics key="collapsed" value="false"/>
				<feature mandatory="true" name="Confirmar Voto">
					<graphics key="position" value="788,359"/>
				</feature>
				<feature mandatory="true" name="Corrigir Voto">
					<graphics key="position" value="788,388"/>
				</feature>
				<feature name="Branco">
					<graphics key="position" value="788,417"/>
				</feature>
				<feature name="Nulo">
					<graphics key="position" value="788,446"/>
				</feature>
				<feature name="Peso de Voto">
					<graphics key="position" value="788,475"/>
				</feature>
			</and>
		</and>
		<and abstract="true" mandatory="true" name="Gerenciar Urna">
			<graphics key="position" value="528,496"/>
			<graphics key="collapsed" value="false"/>
			<feature mandatory="true" name="Abrir Votação">
				<graphics key="position" value="652,482"/>
			</feature>
			<feature mandatory="true" name="Terminar Votação">
				<graphics key="position" value="652,511"/>
			</feature>
		</and>
		<and mandatory="true" name="Cadastro">
			<graphics key="position" value="528,591"/>
			<graphics key="collapsed" value="false"/>
			<feature name="De Eleitor">
				<graphics key="position" value="652,576"/>
			</feature>
			<or mandatory="true" name="De Candidato">
				<graphics key="position" value="652,605"/>
				<graphics key="collapsed" value="false"/>
				<feature name="Deputado Federal">
					<graphics key="position" value="788,504"/>
				</feature>
				<feature name="Deputado Estadual">
					<graphics key="position" value="788,533"/>
				</feature>
				<feature name="Presidente">
					<graphics key="position" value="788,562"/>
				</feature>
				<feature name="Senador">
					<graphics key="position" value="788,591"/>
				</feature>
				<feature name="Vereador">
					<graphics key="position" value="788,620"/>
				</feature>
				<feature name="Prefeito">
					<graphics key="position" value="788,649"/>
				</feature>
				<feature name="Representante Universitário">
					<graphics key="position" value="788,678"/>
				</feature>
				<feature name="Participante Reality Show">
					<graphics key="position" value="788,707"/>
				</feature>
			</or>
		</and>
	</and>
</struct>
</featureModel>

