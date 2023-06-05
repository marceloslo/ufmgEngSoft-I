## Dependencias

- [Java Developer Kit (JDK) 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- Eclipse
- FeatureIDE / Antenna

# Como executar o projeto?

## Primeiro Passo: Instalação do Eclipse
1. Faça o download do Eclipse, que é uma plataforma de desenvolvimento integrado (IDE) amplamente utilizada. O repositório da IDE pode ser encontrado em: https://www.eclipse.org/downloads/
2. Execute o instalador do Eclipse e siga as instruções fornecidas para concluir a instalação.
3. Após a instalação, abra o Eclipse.

## Segundo passo: Instalar o FeatureIDE

1. No Eclipse, vá para "Help" (Ajuda) no menu superior.
2. Selecione "Eclipse Marketplace" (Mercado do Eclipse).
3. Na caixa de pesquisa, digite "FeatureIDE" e pressione Enter.
4. Localize o FeatureIDE na lista de resultados e clique em "Go".
5. Clique no botão "Install" (Instalar) ao lado do FeatureIDE.
6. Siga as instruções na tela para concluir a instalação do FeatureIDE.

## Terceiro passo: Realizar o clone do projeto do GitHub na sua máquina local
1. Acesse o GitHub em seu navegador e navegue até o repositório que deseja clonar.
2. Clique no botão "Code" e copie o URL do repositório.
3. Abra o Git Bash ou o terminal de sua preferência no seu computador.
4. Navegue até o diretório onde deseja armazenar o projeto clonado usando o comando "cd" seguido do caminho do diretório.
5. Execute o comando "git clone" seguido do URL do repositório copiado. Por exemplo:
```
git clone https://github.com/marceloslo/ufmgEngSoft-I/edit/main/README.md
```
6. Aguarde enquanto o Git clona o repositório para o seu diretório local.

## Quarto passo: Importar o projeto do repositório do github
1. No Eclipse, vá para "File" (Arquivo) no menu superior e selecione "Import" (importar).
2. Na aba "General"(Geral), clique em "Existing Projects into Workspace"(Projetos existentes para o espaço de trabalho), depois "Browse"(navegar) e selecione a pasta que contém esse projeto.
3. Digite um nome para o projeto, clique em "Finish" (Concluir).

## Quinto passo: Escolher configuração/produto e executar
1. Clique com o botão direito na configuração desejada. Em "FeatureIde" clique em "Set as current configuration".
2. Execute o projeto no eclipse. 

# Como utilizar o produto

Exemplo de teste de uma Eleição Federal e Estadual.

No menu inicial para gerenciar candidatos e eleição siga pela opção 2:

- User: `emp` , Password: `12345` -> Cadastro e remoção de candidatos da eleição
- User: `cert` , Password: `54321` -> Inicialização/finalização da eleição (liberar pra poder votar) e mostrar o resultado ao final da eleição.

Além da senha de usuário é necessário a senha da eleição para completar operações relacionadas a gestão da eleição ou candidatos. Essa senha é a palavra `password`

Para votar também existe um eleitor com o título de eleitor nº 1 que pode votar nos candidatos pré-cadastrados

## Execução teste

Para uma execução teste podemos seguir o seguinte passo:

- Ao iniciar a aplicação selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 1 e inserir a senha da urna (`password`) para iniciar a votação
- Escolher a opção 0 para voltar ao menu inicial
- Escolher votar (opção 1) e inserir o nº `1` do eleitor de teste
- Selecionar sim e votar respectivamente `15` , `123`, `44`, `12345`, `00`
- Escolher votar (opção 1) e inserir o nº `2` (outro eleitor de teste)
- Selecionar sim e votar respectivamente `30` , `124`, `br`, `54321`, `64`
- No menu inicial, selecionar a opção 2 e logar com o user `cert`
- Escolher a opção 2 e inserir a senha da urna (`password`) para encerrar a votação
- Escolher a opção 3 e inserir a senha da urna (`password`) para mostrar o resultado final da votação
- Escolher a opção 0 duas vezes para encerrar a aplicação
